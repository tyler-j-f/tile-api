import React, {useEffect, useState} from 'react'
import styled from 'styled-components';
import ViewToken from "../view/ViewToken";
import TokenMergeSelector from "../merge/TokenMergeSelector";
import {Button} from "react-bootstrap";

const MergePage = () => {

  const [mergeData, setMergeData] = useState({
    token1: {
      tokenId: '',
      isInvalidTokenNumber: false
    },
    token2: {
      tokenId: '',
      isInvalidTokenNumber: false
    },
    contractAddress: ''
  });

  useEffect(
      () => {
        console.log("debug mergeData", mergeData)
      },
      [mergeData]
  );

  const handleToken1Selected = ({
    tokenId: token1IdToSet,
    isInvalidTokenNumber: token1IdIsInvalid,
    contractAddress: contractAddressToSet
  }) => {
    setMergeData({
      token1: {
        ...mergeData.token1,
        tokenId: token1IdToSet,
        isInvalidTokenNumber: token1IdIsInvalid
      },
      token2: mergeData.token2,
      contractAddress: contractAddressToSet
    });
  }

  const handleToken2Selected = ({
    tokenId: token2IdToSet,
    isInvalidTokenNumber: token2IdIsInvalid,
    contractAddress: contractAddressToSet
  }) => {
    setMergeData({
      token1: mergeData.token1,
      token2: {
        ...mergeData.token2,
        tokenId: token2IdToSet,
        isInvalidTokenNumber: token2IdIsInvalid
      },
      contractAddress: contractAddressToSet
    });
  }

  const getIsToken1IdValidAndSelected = () => {
    return mergeData?.token1?.tokenId !== '' && !mergeData?.token1?.isInvalidTokenNumber;
  }

  const getIsToken2IdValidAndSelected = () => {
    return mergeData?.token2?.tokenId !== '' && !mergeData?.token2?.isInvalidTokenNumber;
  }

  const getShouldShowSendTransaction = () => {
    return getIsToken1IdValidAndSelected() && getIsToken2IdValidAndSelected();
  }

    return (
      <StyledPage>
        <Heading className="animate__animated animate__fadeInLeft">Merge A TileNft</Heading>
        <TokenMergeSelector
            headerText={'Select first tile to merge.'}
            tokenLoadedCallback={handleToken1Selected}
        />
        <TokenMergeSelector
            headerText={'Select second tile to merge.'}
            tokenLoadedCallback={handleToken2Selected}
        />
        {getShouldShowSendTransaction() &&
          <Button>
            Send Transaction
          </Button>
        }
      </StyledPage>
  )
}

const StyledPage = styled.div`
    min-height: 100vh;
    width: 100vw;
    background-color: #282c34;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;

const Heading = styled.h1`
    font-size: clamp(3rem, 5vw, 7vw);
    color: #eee;
    font-weight: 700;
    margin: 0;
    padding: 0;

    user-select: none; /* supported by Chrome and Opera */
   -webkit-user-select: none; /* Safari */
   -khtml-user-select: none; /* Konqueror HTML */
   -moz-user-select: none; /* Firefox */
   -ms-user-select: none; /* Internet Explorer/Edge */
`;

const StyledText =
    styled.p`
    color: white;
    font-weight: bold;
    `;

export default MergePage
