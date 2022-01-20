import React, {useEffect, useState} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';
import StyledLabel from "../styledComponents/StyledLabel";
import TotalTokens from "../view/TotalTokens";

const LeaderboardPagination = ({leaderboardData, setLeaderboardData, loadLeaderboardData}) => {

  const setPageAndLoad = (pageNumber) => {
    setLeaderboardData({
      ...leaderboardData,
      paginationPage: pageNumber
    });
    loadLeaderboardData(pageNumber);
  }

  const decrementPageAndLoad = () => {
    let pageNumber = leaderboardData.paginationPage;
    decrementPage();
    loadLeaderboardData(pageNumber - 1);
  }

  const incrementPageAndLoad = () => {
    let pageNumber = leaderboardData.paginationPage;
    incrementPage();
    loadLeaderboardData(pageNumber + 1);
  }

  const incrementPageTwiceAndLoad = () => {
    let pageNumber = leaderboardData.paginationPage;
    incrementPageTwice();
    loadLeaderboardData(pageNumber + 2);
  }

  const incrementPage = () => {
    let currentPage = leaderboardData.paginationPage;
    if (currentPage < leaderboardData.maxPaginationPage) {
      setLeaderboardData({
        ...leaderboardData,
        paginationPage: currentPage + 1
      });
    }
  }

  const incrementPageTwice = () => {
    let currentPage = leaderboardData.paginationPage;
    if (currentPage < leaderboardData.maxPaginationPage - 1) {
      setLeaderboardData({
        ...leaderboardData,
        paginationPage: currentPage + 2
      });
    }
  }

  const decrementPage = () => {
    let currentPage = leaderboardData.paginationPage;
    console.log(
        "Should be decrementing page num: " + currentPage
    )
    if (currentPage > 1) {
      setLeaderboardData({
        ...leaderboardData,
        paginationPage: currentPage - 1
      });
    }
    console.log(
        "Post decrement page num: " + currentPage
    )
  }

  const getCurrentPageButton = () => {
    return (
        <li className="page-item"><StyledCurrentPageAnchor className="page-link" >{leaderboardData.paginationPage}</StyledCurrentPageAnchor>
        </li>
    );
  }

  const getPreviousPageButton = () => {
    return (
        <li className="page-item"><a className="page-link" onClick={decrementPageAndLoad.bind(this)}>Previous</a></li>
    );
  }

  const getPageOneButton = () => {
    return (
        <li className="page-item"><a className="page-link" onClick={() => setPageAndLoad.bind(this)(1)}>1</a></li>
    );
  }

  const getPageMinusOneButton = () => {
    return (
        <li className="page-item"><a className="page-link" onClick={decrementPageAndLoad.bind(this)}>{leaderboardData.paginationPage - 1}</a></li>
    );
  }

  const getNextPageButton = () => {
    return (
        <li className="page-item"><a className="page-link" onClick={incrementPageAndLoad.bind(this)}>Next</a>
        </li>
    );
  }

  const getPagePlusOneButton = () => {
    return (
        <li className="page-item"><a className="page-link" onClick={incrementPageAndLoad.bind(this)}>{leaderboardData.paginationPage + 1}</a>
        </li>
    );
  }

  const getPagePlusTwoButton = () => {
    return (
        <li className="page-item"><a className="page-link" onClick={incrementPageTwiceAndLoad.bind(this)}>{leaderboardData.paginationPage + 2}</a>
        </li>
    );
  }

  const getLastPageButton = () => {
    return (
        <li className="page-item"><a className="page-link" onClick={() => setPageAndLoad.bind(this)(leaderboardData.maxPaginationPage)}>{leaderboardData.maxPaginationPage}</a></li>
    );
  }

  let currentPage = leaderboardData.paginationPage;
  let shouldShowPreviousPageButton = currentPage > 1;
  let shouldShowPageOneButton = currentPage > 2;
  let previousPageButton = shouldShowPreviousPageButton ? getPreviousPageButton() : null;
  let pageOneButton = shouldShowPageOneButton ? getPageOneButton() : null;
  let pageMinusOneButton = shouldShowPreviousPageButton ? getPageMinusOneButton() : null;
  let shouldShowNextPageButton = currentPage < leaderboardData.maxPaginationPage;
  let nextPageButton = shouldShowNextPageButton ? getNextPageButton() : null;
  if (previousPageButton == null && nextPageButton == null) {
    return null;
  }
  let pagePlusOneButton = shouldShowNextPageButton ? getPagePlusOneButton() : null;
  let shouldShowPagePlusTwoButton = currentPage + 1 < leaderboardData.maxPaginationPage;
  let pagePlusTwoButton = shouldShowPagePlusTwoButton ? getPagePlusTwoButton() : null;
  let shouldShowLastPageButton = currentPage + 2 < leaderboardData.maxPaginationPage;
  let lastPageButton = shouldShowLastPageButton ? getLastPageButton() : null;
  let currentPageButton = getCurrentPageButton()
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

const StyledCurrentPageAnchor =
    styled.a`
    color: darkblue;
    font-weight: bold;
    `;

export default LeaderboardPagination;
