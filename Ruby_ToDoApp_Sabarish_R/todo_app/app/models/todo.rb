class Todo < ApplicationRecord
    def completed
        if read_attribute(:completed)
            return "Yes"
        else
            return "No"
        end
    end
end
