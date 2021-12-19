import {Component} from 'react';

class TokenNumberForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      value: '',
      submittedValue: '',
      shouldShowImage: false
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
    this.setState({value: event.target.value});
  }

  handleSubmit(event) {
    this.setState({submittedValue: this.state.value});
    this.loadTokenImage();
    event.preventDefault();
  }

  loadTokenImage() {
    fetch(`http://localhost:8080/api/image/tile/get/${this.state.value}`, {method: 'get'})
    .then(response => {
      console.log(response);
      if (response.status === 200) {
        console.log("shouldShowImage is true");
        this.setState({shouldShowImage: true});
      }
    })
    .catch(err => {
      this.setState({shouldShowImage: false});
      console.log("Error caught!!!");
      console.log(err)
    });
  }

  render() {
    return (
        <form onSubmit={this.handleSubmit}>
          <label>
            Token Number:&nbsp;
            <input type="number" value={this.state.value} onChange={this.handleChange} />
          </label>
          <input type="submit" value="Submit" />
          {
            this.state.shouldShowImage &&
            <img
                src={`http://localhost:8080/api/image/tile/get/${this.state.submittedValue}`}
            />
          }
        </form>
    );
  }
}

export default TokenNumberForm;
