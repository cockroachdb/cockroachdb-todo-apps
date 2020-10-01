let todoItems = [];
const endpoint = '/api/todo'

function renderTodo(todo) {
  const list = document.querySelector('.js-todo-list');
  const item = document.querySelector(`[data-key='${todo.id}']`);
  if (todo.deleted) {
    console.log(item);
    item.remove();
    if (todoItems.length === 0) list.innerHTML = '';
    return
  }

  const isChecked = todo.checked ? 'done': '';
  const node = document.createElement("li");
  node.setAttribute('class', `todo-item ${isChecked}`);
  node.setAttribute('data-key', todo.id);
  node.innerHTML = `
    <input id="${todo.id}" type="checkbox"/>
    <label for="${todo.id}" class="tick js-tick"></label>
    <span>${todo.text}</span>
    <button class="delete-todo js-delete-todo">
    <svg><use href="#delete-icon"></use></svg>
    </button>
  `;

  if (item) {
    list.replaceChild(node, item);
  } else {
    list.append(node);
  }
}

function addTodo(text) {
  const todo = {
    text: text,
    checked: false,
  };

  // input data
  fetch(endpoint, {
    method: 'post',
    body: JSON.stringify(todo)
  })
    .then(res => res.json())
    .then(res => {
      console.log(res);
      todo.id = res.id;
      todoItems.push(todo);
      renderTodo(todo);
    })
    .catch(function(error) {
      console.log(error);
    });
}

function toggleDone(key) {
  const index = todoItems.findIndex(item => item.id === key);
  todoItems[index].checked = !todoItems[index].checked;
  // input data
  fetch(endpoint, {
    method: 'put',
    body: JSON.stringify(todoItems[index])
  })
    .then(res => res.json())
    .then(res => {
      console.log(res);
      renderTodo(todoItems[index]);
    })
    .catch(function(error) {
      console.log(error);
    });
}

function deleteTodo(key) {
  // delete data
  fetch(endpoint + '/' + key, {
    method: 'DELETE',
  })
    .then(res => res.json()) // or res.json()
    .then(res => console.log(res))
    .catch(function(error) {
      console.log(error);
    });
  const index = todoItems.findIndex(item => item.id === key);
  const todo = {
    deleted: true,
    ...todoItems[index]
  };
  todoItems = todoItems.filter(item => item.id !== key);
  renderTodo(todo);
}

const form = document.querySelector('.js-form');
form.addEventListener('submit', event => {
  event.preventDefault();
  const input = document.querySelector('.js-todo-input');

  const text = input.value.trim();
  if (text !== '') {
    addTodo(text);
    input.value = '';
    input.focus();
  }
});

const list = document.querySelector('.js-todo-list');
list.addEventListener('click', event => {
  if (event.target.classList.contains('js-tick')) {
    const itemKey = event.target.parentElement.dataset.key;
    toggleDone(itemKey);
  }
  
  if (event.target.classList.contains('js-delete-todo')) {
    const itemKey = event.target.parentElement.dataset.key;
    console.log(itemKey);
    deleteTodo(itemKey);
  }
});

document.addEventListener('DOMContentLoaded', () => {
  // fetch data
  fetch(endpoint)
    .then((resp) => resp.json())
    .then(function(data) {
      console.log(data)
      if (data) {
        todoItems = data;
        todoItems.forEach(t => {
          renderTodo(t);
        });
      }
    })
    .catch(function(error) {
      console.log(error);
    });
});