import React, {useEffect, useState} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';
import StyledLabel from "../styledComponents/StyledLabel";
import TotalTokens from "../view/TotalTokens";

const Leaderboard = () => {

  const [leaderboardData, setLeaderboardData] = useState({
    isGeneralError: false,
    isLoading: false,
    leaders: [],
    itemsPerPage: 10,
    paginationPage: 1,
    maxPaginationPage: 10,
    totalUnburntTokens: null,
    totalTokens: null
  });

  useEffect(
      () => {
        loadLeaderboardData(1);
      },
      []
  );

  const getTotalTokens = () => {
    return (
        <TotalTokens
            totalUnburntTokens={leaderboardData.totalUnburntTokens}
            totalTokens={leaderboardData.totalTokens}
            useLoadData
        />
    )
  }

  const getLeaderboardUrl = (pageNumber) => {
    let startIndex = (pageNumber - 1) * leaderboardData.itemsPerPage;
    let endIndex = startIndex + leaderboardData.itemsPerPage;
    return `${window.location.origin}/api/leaderboard/getLeaders?startIndex=${startIndex}&endIndex=${endIndex}`;
  }

  const loadLeaderboardData = (pageNumber) => {
    setLeaderboardData({
      ...leaderboardData,
      isLoading: true
    });
    return loadNumberOfTokens().then(() => loadLeaders(pageNumber))
  }

  const loadNumberOfTokens = () => {
    return fetch(
        `${window.location.origin}/api/leaderboard/getNumberOfUnburntTokens`,
        {method: 'get'}
    )
    .then(response => {
      console.log(response);
      return response.json();
    })
    .then(numberOfTokens => {
      let maxPaginationPage = Math.ceil(numberOfTokens/leaderboardData.itemsPerPage);
      setLeaderboardData({
        ...leaderboardData,
        maxPaginationPage: maxPaginationPage,
        isLoading: false,
        isGeneralError: false
      });
    })
    .catch(err => {
      setLeaderboardData({
        ...leaderboardData,
        isLoading: false,
        isGeneralError: true
      });
      console.log("Error caught!!!");
      console.log(err)
    });
  }

  const loadLeaders = (pageNumber) => {
    return fetch(getLeaderboardUrl(pageNumber), {method: 'get'})
    .then(response => {
      console.log(response);
      return response.json();
    })
    .then(leaders => {
      setLeaderboardData({
        ...leaderboardData,
        leaders,
        isLoading: false,
        isGeneralError: false
      });
    })
    .catch(err => {
      setLeaderboardData({
        ...leaderboardData,
        isLoading: false,
        isGeneralError: true
      });
      console.log("Error caught!!!");
      console.log(err)
    });
  }

  const getLeaderboard = () => {
    if (leaderboardData.isGeneralError) {
      return getGeneralErrorText();
    }
    let leaderboard =  leaderboardData.leaders.map(
        (
            {tokenId, rankCount}
        ) => getTokenImage(tokenId, rankCount)
    );
    let pagination = getPagination();
    let totalTokens = getTotalTokens();
    return (
        <>
          {totalTokens}
          {leaderboard}
          {pagination}
        </>
    );
  }

  const getGeneralErrorText = () => {
    return (
        <StyledErrorText>Error! Please try again later.</StyledErrorText>
    )
  }

  const getTokenImage = (tokenId, rank) => {
    return (
        <div>
          <StyledLabel># {rank}</StyledLabel>
          <StyledImg imgSource={`${window.location.origin}/api/image/tile/get/${tokenId}`} />
        </div>
    );
  }

  const getSpinner = () => {
    return (
        <Spinner animation="border" variant="primary" />
    );
  }

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

  const getPagination = () => {
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

  let isLoading = leaderboardData.isLoading && leaderboardData.leaders !== [];
  let loadingSymbol = isLoading ? getSpinner() : null;
  let leaderboard = isLoading ? null : getLeaderboard();
  return isLoading ? loadingSymbol : leaderboard;
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
