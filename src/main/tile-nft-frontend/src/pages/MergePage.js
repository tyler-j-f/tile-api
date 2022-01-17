import React, {useState} from 'react'
import styled from 'styled-components';
import TokenMergeSelector from "../merge/TokenMergeSelector";
import {useEthers} from "@usedapp/core";
import ConnectButton from "../updateTileNft/ConnectButton";
import MergeTxWrapper from "../merge/MergeTxWrapper";
import TransactionSuccess from "../etc/TransactionSuccess";
import StyledPage from "../styledComponents/StyledPage";
import PageHeader from "../styledComponents/PageHeader";

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
        <PageHeader>Merge TileNFTs</PageHeader>
        {mergeData?.txStatus?.isSuccess && getSuccessfulTx()}
        {!mergeData?.txStatus?.isSuccess && getBody()}
      </StyledPage>
  )
}

export default MergePage
