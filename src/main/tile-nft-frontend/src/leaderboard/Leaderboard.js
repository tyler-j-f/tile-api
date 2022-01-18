import React, {Component} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';
import {forEach} from "react-bootstrap/ElementChildren";
import StyledLabel from "../styledComponents/StyledLabel";
import TotalTokens from "../view/TotalTokens";

class Leaderboard extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isGeneralError: false,
      isLoading: false,
      leaders: [],
      itemsPerPage: 10,
      paginationPage: 1,
      maxPaginationPage: 10,
      totalUnburntTokens: null,
      totalTokens: null
    };
  }

  componentDidMount() {
    this.loadLeaderboardData(1);
  }

  render() {
    let isLoading = this.state.isLoading && this.state.leaders !== [];
    let loadingSymbol = isLoading ? this.getSpinner() : null;
    let leaderboard = isLoading ? null : this.getLeaderboard();
    return isLoading ? loadingSymbol : leaderboard;
  }

  getTotalTokens() {
    return (
        <TotalTokens
            totalUnburntTokens={this.state.totalUnburntTokens}
            totalTokens={this.state.totalTokens}
        />
    )
  }

  getLeaderboardUrl(pageNumber) {
    let startIndex = (pageNumber - 1) * this.state.itemsPerPage;
    let endIndex = startIndex + this.state.itemsPerPage;
    return `${window.location.origin}/api/leaderboard/getLeaders?startIndex=${startIndex}&endIndex=${endIndex}`;
  }

  loadLeaderboardData(pageNumber) {
    this.setState({
      isLoading: true
    });
    return this.loadNumberOfTokens().then(() => this.loadLeaders(pageNumber))
  }

  loadNumberOfTokens() {
    return fetch(
        `${window.location.origin}/api/leaderboard/getNumberOfUnburntTokens`,
        {method: 'get'}
    )
    .then(response => {
      console.log(response);
      return response.json();
    })
    .then(numberOfTokens => {
      let maxPaginationPage = Math.ceil(numberOfTokens/this.state.itemsPerPage);
      this.setState({
        maxPaginationPage: maxPaginationPage,
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

  loadLeaders(pageNumber) {
    return fetch(this.getLeaderboardUrl(pageNumber), {method: 'get'})
    .then(response => {
      console.log(response);
      return response.json();
    })
    .then(leaders => {
      this.setState({
        leaders,
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
    let leaderboard =  this.state.leaders.map(
        (
            {tokenId, rankCount}
        ) => this.getTokenImage(tokenId, rankCount)
    );
    let pagination = this.getPagination();
    let totalTokens = this.getTotalTokens();
    return (
        <>
          {totalTokens}
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

  getTokenImage(tokenId, rank) {
    return (
        <div>
          <StyledLabel># {rank}</StyledLabel>
          <StyledImg imgSource={`${window.location.origin}/api/image/tile/get/${tokenId}`} />
        </div>
    );
  }

  getSpinner() {
    return (
        <Spinner animation="border" variant="primary" />
    );
  }

  setPageAndLoad(pageNumber) {
    this.setState({
      paginationPage: pageNumber
    });
    this.loadLeaderboardData(pageNumber);
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
      this.setState({
        paginationPage: currentPage + 1
      });
    }
  }

  incrementPageTwice() {
    let currentPage = this.state.paginationPage;
    if (currentPage < this.state.maxPaginationPage - 1) {
      this.setState({
        paginationPage: currentPage + 2
      });
    }
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
    let shouldShowPageOneButton = currentPage > 2;
    let previousPageButton = shouldShowPreviousPageButton ? this.getPreviousPageButton() : null;
    let pageOneButton = shouldShowPageOneButton ? this.getPageOneButton() : null;
    let pageMinusOneButton = shouldShowPreviousPageButton ? this.getPageMinusOneButton() : null;
    let shouldShowNextPageButton = currentPage < this.state.maxPaginationPage;
    let nextPageButton = shouldShowNextPageButton ? this.getNextPageButton() : null;
    if (previousPageButton == null && nextPageButton == null) {
      return null;
    }
    let pagePlusOneButton = shouldShowNextPageButton ? this.getPagePlusOneButton() : null;
    let shouldShowPagePlusTwoButton = currentPage + 1 < this.state.maxPaginationPage;
    let pagePlusTwoButton = shouldShowPagePlusTwoButton ? this.getPagePlusTwoButton() : null;
    let shouldShowLastPageButton = currentPage + 2 < this.state.maxPaginationPage;
    let lastPageButton = shouldShowLastPageButton ? this.getLastPageButton() : null;
    let currentPageButton = this.getCurrentPageButton()
    return (
        <nav aria-label="Page navigation example">
          <ul className="pagination">
            {previousPageButton}
            {pageOneButton}
            {pageMinusOneButton}
            {currentPageButton}
            {pagePlusOneButton}
            {pagePlusTwoButton}
            {lastPageButton}
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


  getPageOneButton() {
    return (
        <li className="page-item"><a className="page-link" onClick={() => this.setPageAndLoad.bind(this)(1)}>1</a></li>
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

  getLastPageButton() {
    return (
        <li className="page-item"><a className="page-link" onClick={() => this.setPageAndLoad.bind(this)(this.state.maxPaginationPage)}>{this.state.maxPaginationPage}</a></li>
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
    display: block;
    `;

const StyledErrorText =
    styled.p`
    display: block;
    color: #FF4500;
    text-align: center;
    `;

const StyledCurrentPageAnchor =
    styled.a`
    color: darkblue;
    font-weight: bold;
    `;

export default Leaderboard;
