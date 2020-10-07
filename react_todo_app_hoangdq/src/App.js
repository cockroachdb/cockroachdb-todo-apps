import React, { Component } from 'react';
import ToDoListItem from "./ToDoListItem.js"
import './App.css';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      count: 0,
      inputItem: {
        id: 0,
        title: '',
        description: ''
      },
      todoListItems: []
    };
  }

  onTitleChange = (event) => {
    this.setState({
      inputItem: {
        id: this.state.count,
        title: event.target.value,
        description: this.state.inputItem.description
      }
    });
  }

  onDescriptionChange = (event) => {
    this.setState({
      inputItem: {
        id: this.state.count,
        title: this.state.inputItem.title,
        description: event.target.value
      }
    });
  }

  handleAdd = (event) => {
    event.preventDefault();
    this.setState({
      count: this.state.count + 1,
      inputItem: {
        id: this.state.count,
        title: '',
        description: ''
      },
      todoListItems: [
        ...this.state.todoListItems,
        this.state.inputItem
      ]
    })
  }

  handleDelete = (removeItem) => {
    let newTodoListItems = this.state.todoListItems.filter(item => {
      return item.id !== removeItem.id;
    })
    this.setState({
      todoListItems: newTodoListItems
    })
  }

  render() {
    return (
      <div className="App">
        <h1>Plan Your Day</h1>
        <form className="App-form">
          <input
            type="text"
            value={this.state.inputItem.title}
            placeholder="Title Here!"
            onChange={this.onTitleChange}
          />
          <textarea
            value={this.state.inputItem.description}
            placeholder= "Description"
            onChange={this.onDescriptionChange}
          />
          <button className="button" onClick={this.handleAdd}>Save</button>
        </form>
        <div>
          {
            this.state.todoListItems.map((item, index) =>
              <div onClick={this.handleDelete.bind(this, item)} key={index}>
                <ToDoListItem
                  title={item.title}
                  description={item.description}
                />
              </div>
            )
          }
        </div>
      </div>
    );
  }
}

export default App;
