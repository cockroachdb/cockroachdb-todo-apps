# (C) 2020 Emmanuel Salcido
from django.http import JsonResponse, HttpResponse
from django.utils.decorators import method_decorator
from django.views.generic import View
from django.views.decorators.csrf import csrf_exempt
from django.db import Error, IntegrityError
from django.db.transaction import atomic

import json
import sys
import time

from .models import *

# Warning: Do not use retry_on_exception in an inner nested transaction.
def retry_on_exception(num_retries=3, on_failure=HttpResponse(status=500), delay_=0.5, backoff_=1.5):
    def retry(view):
        def wrapper(*args, **kwargs):
            delay = delay_
            for i in range(num_retries):
                try:
                    return view(*args, **kwargs)
                except IntegrityError as ex:
                    if i == num_retries - 1:
                        return on_failure
                    elif getattr(ex.__cause__, 'pgcode', '') == errorcodes.SERIALIZATION_FAILURE:
                        time.sleep(delay)
                        delay *= backoff_
                except Error as ex:
                    return on_failure
        return wrapper
    return retry

class PingView(View):
    def get(self, request, *args, **kwargs):
        return HttpResponse("python/django", status=200)


@method_decorator(csrf_exempt, name='dispatch')
class ItemView(View):
    def get(self, request, id=None, *args, **kwargs):
        if id is None:
            items = list(Items.objects.values())
        else:
            items = list(Items.objects.filter(id=id).values())
        return JsonResponse(items, safe=False)

    @retry_on_exception(3)
    @atomic
    def post(self, request, *args, **kwargs):
        from_data = json.loads(request.body.decode())
        description = from_data['description']
        item = Items(description=description)
        item.save()
        return HttpResponse(status=201)

    @retry_on_exception(3)
    @atomic
    def put(self, request, id=None, *args, **kwargs):
        if id is None:
            return HttpResponse(status=400)
        else:
            from_data = json.loads(request.body.decode())
            update_description = from_data['description']
            update_item = Items(id=id, description=update_description)
            update_item.save()
            return HttpResponse(status=200)
            

    @retry_on_exception(3)
    @atomic
    def delete(self, request, id=None, *args, **kwargs):
        if id is None:
            return HttpResponse(status=404)
        Items.objects.filter(id=id).delete()
        return HttpResponse(status=200)