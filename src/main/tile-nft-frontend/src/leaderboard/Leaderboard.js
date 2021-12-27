import {Component} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';

class Leaderboard extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isGeneralError: false,
      isLoading: false,
      tokenIds: [],
      itemsPerPage: 5,
      paginationPage: 1
    };
  }

  componentDidMount() {
    this.loadLeaderboardData();
  }

  getLeaderboardUrl() {
    let startIndex = (this.state.paginationPage - 1) * this.state.itemsPerPage;
    let endIndex = startIndex + this.state.itemsPerPage;
    return `http://localhost:8080/api/frontend/getLeaders?startIndex=${startIndex}&endIndex=${endIndex}`;
  }

  loadLeaderboardData() {
    this.setState({
      isLoading: true
    });
    fetch(this.getLeaderboardUrl(), {method: 'get'})
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
    let leaderboard =  this.state.tokenIds.map(
        (tokenId, index) => this.getTokenImage(tokenId, index)
    );
    let pagination = this.getPagination();
    return (
        <>
          {leaderboard}
          {pagination}
        </>
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

  getPagination() {
    return (
        <nav aria-label="Page navigation example">
          <ul className="pagination">
            <li className="page-item"><a className="page-link"
                                         href="#">Previous</a></li>
            <li className="page-item"><a className="page-link" href="#">1</a>
            </li>
            <li className="page-item"><a className="page-link" href="#">2</a>
            </li>
            <li className="page-item"><a className="page-link" href="#">3</a>
            </li>
            <li className="page-item"><a className="page-link" href="#">Next</a>
            </li>
          </ul>
        </nav>
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
