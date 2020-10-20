class CreateTodos < ActiveRecord::Migration[5.2]
  def change
    create_table :todos do |t|
      t.string :name
      t.boolean :completed

      t.timestamps
    end
  end
end
