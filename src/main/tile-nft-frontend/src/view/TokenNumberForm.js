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
      isLoading: false,
      isInvalidTokenNumber: false,
      isGeneralError: false,
      imgValue: ''
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
        return response.blob();
      }
      if (response.status !== 200) {
        this.setState({
          shouldShowImage: false,
          isLoading: false,
          isInvalidTokenNumber: true,
          isGeneralError: false,
          imgValue: ''
        });
      }
      return null;
    })
    .then(blob => {
      if (blob === null) {
        console.log('Image blob is null');
        return null;
      }
      this.setState({
        shouldShowImage: true,
        isLoading: false,
        isInvalidTokenNumber: false,
        isGeneralError: false,
        imgValue: URL.createObjectURL(blob)
      });
    })
    .catch(err => {
      this.setState({
        shouldShowImage: false,
        isLoading: false,
        isInvalidTokenNumber: false,
        isGeneralError: true,
        imgValue: ''
      });
      console.log("Error caught!!!");
      console.log(err)
    });
  }

  render() {
    let loadingSymbol = this.state.isLoading ? this.getSpinner() : null;
    let form = this.state.isLoading ? null : this.getForm();
    return this.state.isLoading ? loadingSymbol : form;
  }

  getGeneralErrorText() {
    return (
        <StyledErrorText>Error! Please try again later.</StyledErrorText>
    )
  }

  getTokenNumberErrorText() {
    return (
        <StyledErrorText>Token number does not exist.</StyledErrorText>
    )
  }

  getForm() {
    let formBody = this.getFormBody();
    return (
        <>
          <form onSubmit={this.handleSubmit}>
            <StyledLabel>
              Token Number:&nbsp;
              <input type="number" value={this.state.value} onChange={this.handleChange} />
            </StyledLabel>
            <input type="submit" value="Submit" />
            {formBody}
          </form>
        </>
    );
  }

  getFormBody() {
    if (this.state.isGeneralError) {
      return this.getGeneralErrorText();
    }
    if (this.state.isInvalidTokenNumber) {
      return this.getTokenNumberErrorText();
    }
    return (
      <>
        {
          this.state.shouldShowImage && <StyledImg imgSource={this.state.imgValue} />
        }
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
      src: props.imgSource
    }))`
      width: 350px;
      height: 350px;
      margin: 10px;
      display: block
      `;

const StyledErrorText =
    styled.p`
      display: block;
      color: #FF4500;
      text-align: center;
      `;

const StyledLabel =
    styled.label`
      color: #F8F8FF;
      `;

export default TokenNumberForm;
