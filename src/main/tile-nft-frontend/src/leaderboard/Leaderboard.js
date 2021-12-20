import {Component} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';

class Leaderboard extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isGeneralError: false,
      isLoading: false,
      tokenIds: [9, 5, 3, 8, 4]
    };
  }

  getLeaderboard() {
    if (this.state.isGeneralError) {
      return this.getGeneralErrorText();
    }
    return this.state.tokenIds.map(
        (tokenId, index) => this.getTokenImage(tokenId, index)
    );
  }

  getGeneralErrorText() {
    return (
        <StyledErrorText>Error! Please try again later.</StyledErrorText>
    )
  }

  getTokenImage(tokenId, count) {
    return (
        <>
          <p># {count}</p>
          <StyledImg imgSource={`http://localhost:8080/api/image/tile/get/${tokenId}`} />
        </>
    );
  }

  getSpinner() {
    return (
        <Spinner animation="border" variant="primary" />
    );
  }

  render() {
    let loadingSymbol = this.state.isLoading ? this.getSpinner() : null;
    let leaderboard = this.state.isLoading ? null : this.getLeaderboard();
    return this.state.isLoading ? loadingSymbol : leaderboard;
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

export default Leaderboard;
