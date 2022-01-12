import React, {useEffect, useState} from 'react'
import styled from 'styled-components';
import TokenMergeSelector from "../merge/TokenMergeSelector";
import {Button} from "react-bootstrap";
import {useEthers} from "@usedapp/core";
import ConnectButton from "../updateTileNft/ConnectButton";
import MergeTxWrapper from "../merge/MergeTxWrapper";
import TransactionSuccess from "../etc/TransactionSuccess";

const MergePage = () => {

  const {account} = useEthers();

  const [mergeData, setMergeData] = useState({
    token1: {
      tokenId: '',
      isInvalidTokenNumber: false
    },
    token2: {
      tokenId: '',
      isInvalidTokenNumber: false
    },
    contractAddress: '',
    txStatus: {
      isSuccess: false,
      txId: ''
    }
  });

  const handleToken1Selected = ({
    tokenId: token1IdToSet,
    isInvalidTokenNumber: token1IdIsInvalid,
    contractAddress: contractAddressToSet
  }) => {
    setMergeData({
      ...mergeData,
      token1: {
        ...mergeData.token1,
        tokenId: token1IdToSet,
        isInvalidTokenNumber: token1IdIsInvalid
      },
      contractAddress: contractAddressToSet
    });
  }

  const handleToken2Selected = ({
    tokenId: token2IdToSet,
    isInvalidTokenNumber: token2IdIsInvalid,
    contractAddress: contractAddressToSet
  }) => {
    setMergeData({
      ...mergeData,
      token2: {
        ...mergeData.token2,
        tokenId: token2IdToSet,
        isInvalidTokenNumber: token2IdIsInvalid
      },
      contractAddress: contractAddressToSet
    });
  }

  const handleSuccessfulTx = (txId) => {
    console.log('debug handleSuccessfulTx', txId);
    setMergeData({
      ...mergeData,
      txStatus: {
        isSuccess: true,
        txId: txId
      }
    })
  }

  const getIsToken1IdValidAndSelected = () => {
    return mergeData?.token1?.tokenId !== '' && !mergeData?.token1?.isInvalidTokenNumber;
  }

  const getIsToken2IdValidAndSelected = () => {
    return mergeData?.token2?.tokenId !== '' && !mergeData?.token2?.isInvalidTokenNumber;
  }

  const getShouldShowSendTransaction = () => {
    return account && getIsToken1IdValidAndSelected() && getIsToken2IdValidAndSelected();
  }

  const handleSendAnotherTx = () => {
    return setMergeData({
      ...mergeData,
      txStatus: {
        isSuccess: false,
        txId: ''
      }
    })
  }

  const getBody = () => (
    <>
      <TokenMergeSelector
          headerText={'Select first tile to merge.'}
          tokenLoadedCallback={handleToken1Selected}
      />
      <TokenMergeSelector
          headerText={'Select second tile to merge.'}
          tokenLoadedCallback={handleToken2Selected}
      />
      <ConnectButton />
      {getShouldShowSendTransaction() &&
      <MergeTxWrapper
          tokenId1={mergeData.token1.tokenId}
          tokenId2={mergeData.token2.tokenId}
          contractAddress={mergeData.contractAddress}
          account={account}
          successCallback={handleSuccessfulTx}
      />
      }
    </>
  );

  const getSuccessfulTx = () => {
    return (
        <TransactionSuccess
            handleSendAnotherTx={handleSendAnotherTx}
            txId={mergeData.txStatus.txId}
            subText={'Please wait a few minutes for the transaction to process and the new TileNFT to be sent to account: ' + account}
        />
    );
  };


    return (
      <StyledPage>
        <Heading className="animate__animated animate__fadeInLeft">Merge TileNFTs</Heading>
        {mergeData?.txStatus?.isSuccess && getSuccessfulTx()}
        {!mergeData?.txStatus?.isSuccess && getBody()}
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
