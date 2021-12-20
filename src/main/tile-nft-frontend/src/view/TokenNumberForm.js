import {Component} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';
import '../../node_modules/bootstrap/dist/css/bootstrap.min.css';

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
        this.setState({shouldShowImage: true});
      }
      if (response.status !== 200) {
        this.setState({shouldShowImage: false});
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
