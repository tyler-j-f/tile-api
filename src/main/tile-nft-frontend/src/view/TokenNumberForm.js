import {Component} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';

class TokenNumberForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      value: '',
      submittedValue: '',
      shouldShowImage: false,
      isLoading: false
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
    this.setState({value: event.target.value});
  }

  handleSubmit(event) {
    this.setState({
      submittedValue: this.state.value,
      isLoading: true
    });
    this.loadTokenImage();
    event.preventDefault();
  }

  loadTokenImage() {
    fetch(`http://localhost:8080/api/image/tile/get/${this.state.value}`, {method: 'get'})
    .then(response => {
      console.log(response);
      if (response.status === 200) {
        this.setState({
          shouldShowImage: true,
          isLoading: false
        });
      }
      if (response.status !== 200) {
        this.setState({
          shouldShowImage: false,
          isLoading: false
        });
      }
    })
    .catch(err => {
      this.setState({
        shouldShowImage: false,
        isLoading: false
      });
      console.log("Error caught!!!");
      console.log(err)
    });
  }

  render() {
    let loadingSymbol = this.state.isLoading ? this.getSpinner() : null;
    let formBody = this.state.isLoading ? null : this.getFormBody();
    return this.state.isLoading ? loadingSymbol : formBody;
  }

  getFormBody() {
    return (
        <>
          <form onSubmit={this.handleSubmit}>
            <label>
              Token Number:&nbsp;
              <input type="number" value={this.state.value} onChange={this.handleChange} />
            </label>
            <input type="submit" value="Submit" />
            {
              this.state.shouldShowImage && <StyledImg imgSource={this.state.submittedValue} />
            }
          </form>
        </>
    );
  }

  getSpinner() {
    return (
        <Spinner animation="border" variant="primary" />
    );
  }

}

const StyledImg =
    styled.img.attrs(props => ({
      src: `http://localhost:8080/api/image/tile/get/${props.imgSource}`
    }))`
      width: 350px;
      height: 350px;
      margin: 10px;
      `;

export default TokenNumberForm;
