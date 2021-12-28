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
      paginationPage: 1,
      maxPaginationPage: 2
    };
  }

  componentDidMount() {
    this.loadLeaderboardData(1);
  }

  render() {
    let isLoading = this.state.isLoading && this.state.tokenIds !== [];
    let loadingSymbol = isLoading ? this.getSpinner() : null;
    let leaderboard = isLoading ? null : this.getLeaderboard();
    return isLoading ? loadingSymbol : leaderboard;
  }

  getLeaderboardUrl(startPageNumber) {
    let startIndex = (startPageNumber - 1) * this.state.itemsPerPage;
    let endIndex = startIndex + this.state.itemsPerPage;
    return `http://localhost:8080/api/frontend/getLeaders?startIndex=${startIndex}&endIndex=${endIndex}`;
  }

  loadLeaderboardData(startPageNumber) {
    this.setState({
      isLoading: true
    });
    fetch(this.getLeaderboardUrl(startPageNumber), {method: 'get'})
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

  decrementPageAndLoad() {
    let pageNumber = this.state.paginationPage;
    this.decrementPage();
    this.loadLeaderboardData(pageNumber - 1);
  }


  incrementPageAndLoad() {
    let pageNumber = this.state.paginationPage;
    this.incrementPage();
    this.loadLeaderboardData(pageNumber + 1);
  }

  incrementPageTwiceAndLoad() {
    let pageNumber = this.state.paginationPage;
    this.incrementPageTwice();
    this.loadLeaderboardData(pageNumber + 2);
  }

  incrementPage() {
    let currentPage = this.state.paginationPage;
    if (currentPage < this.state.maxPaginationPage) {
      console.log(
          "Should be incrementing page num: " + currentPage
      )
      this.setState({
        paginationPage: currentPage + 1
      });
      console.log(
          "Post increment page num: " + this.state.paginationPage
      )
    }
  }

  incrementPageTwice() {
    this.incrementPage.bind(this);
    this.incrementPage.bind(this);
  }

  decrementPage() {
    let currentPage = this.state.paginationPage;
    console.log(
        "Should be decrementing page num: " + currentPage
    )
    if (currentPage > 1) {
      this.setState({
        paginationPage: currentPage - 1
      });
    }
    console.log(
        "Post decrement page num: " + currentPage
    )
  }

  getPagination() {
    let currentPage = this.state.paginationPage;
    let shouldShowPreviousPageButton = currentPage > 1;
    let previousPageButton = shouldShowPreviousPageButton ? this.getPreviousPageButton() : null;
    let pageMinusOneButton = shouldShowPreviousPageButton ? this.getPageMinusOneButton() : null;
    let shouldShowNextPageButton = currentPage < this.state.maxPaginationPage;
    let nextPageButton = shouldShowNextPageButton ? this.getNextPageButton() : null;
    if (previousPageButton == null && nextPageButton == null) {
      return null;
    }
    let pagePlusOneButton = shouldShowNextPageButton ? this.getPagePlusOneButton() : null;
    let shouldShowPagePlusTwoButton = currentPage + 1 < this.state.maxPaginationPage;
    let pagePlusTwoButton = shouldShowPagePlusTwoButton ? this.getPagePlusTwoButton() : null;
    let currentPageButton = this.getCurrentPageButton()
    return (
        <nav aria-label="Page navigation example">
          <ul className="pagination">
            {previousPageButton}
            {pageMinusOneButton}
            {currentPageButton}
            {pagePlusOneButton}
            {pagePlusTwoButton}
            {nextPageButton}
          </ul>
        </nav>
    );
  }

  getCurrentPageButton() {
    return (
        <li className="page-item"><StyledCurrentPageAnchor className="page-link" >{this.state.paginationPage}</StyledCurrentPageAnchor>
        </li>
    );
  }

  getPreviousPageButton() {
    return (
        <li className="page-item"><a className="page-link" onClick={this.decrementPageAndLoad.bind(this)}>Previous</a></li>
    );
  }

  getPageMinusOneButton() {
    return (
        <li className="page-item"><a className="page-link" onClick={this.decrementPageAndLoad.bind(this)}>{this.state.paginationPage - 1}</a></li>
    );
  }

  getNextPageButton() {
    return (
        <li className="page-item"><a className="page-link" onClick={this.incrementPageAndLoad.bind(this)}>Next</a>
        </li>
    );
  }

  getPagePlusOneButton() {
    return (
        <li className="page-item"><a className="page-link" onClick={this.incrementPageAndLoad.bind(this)}>{this.state.paginationPage + 1}</a>
        </li>
    );
  }

  getPagePlusTwoButton() {
    return (
        <li className="page-item"><a className="page-link" onClick={this.incrementPageTwiceAndLoad.bind(this)}>{this.state.paginationPage + 2}</a>
        </li>
    );
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

const StyledCurrentPageAnchor =
    styled.a`
    color: darkblue;
    font-weight: bold;
    `;

export default Leaderboard;
