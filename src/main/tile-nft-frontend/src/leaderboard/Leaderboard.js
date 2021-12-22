import {Component} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';

class Leaderboard extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isGeneralError: false,
      isLoading: false,
      tokenIds: []
    };
  }

  componentDidMount() {
    this.loadLeaderboardData();
  }

  loadLeaderboardData() {
    this.setState({
      isLoading: true
    });
    fetch(`http://localhost:8080/api/frontend/getLeaders`, {method: 'get'})
    .then(response => {
      console.log(response);
      return response.json();
    })
    .then(tokenIds => {
      let output = tokenIds.replace('[', '').replace(']', '').split(', ')
      console.log("leaderboard tokenIds", output);
      this.setState({
        tokenIds: output,
        isLoading: false,
        isGeneralError: false
      });
    })
    .catch(err => {
      this.setState({
        isLoading: false,
        isGeneralError: true
      });
      console.log("Error caught!!!");
      console.log(err)
    });
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

  getTokenImage(tokenId, index) {
    return (
        <div>
          <StyledLabel># {index + 1}</StyledLabel>
          <StyledImg imgSource={`http://localhost:8080/api/image/tile/get/${tokenId}`} />
        </div>
    );
  }

  getSpinner() {
    return (
        <Spinner animation="border" variant="primary" />
    );
  }

  render() {
    let isLoading = this.state.isLoading && this.state.tokenIds !== [];
    let loadingSymbol = isLoading ? this.getSpinner() : null;
    let leaderboard = isLoading ? null : this.getLeaderboard();
    return isLoading ? loadingSymbol : leaderboard;
  }
}

const StyledImg =
    styled.img.attrs(props => ({
      src: props.imgSource
    }))`
    @media screen and (min-width: 501px) {
      width: 350px;
      height: 350px;
    }
    @media screen and (max-width: 500px) and (min-width: 321px) {
      width: 200px;
      height: 200px;
    }
    @media screen and (max-width: 320px) {
      width: 150px;
      height: 150px;
    }
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
    margin: 0px 0px 0px 10px;
    `;

export default Leaderboard;
