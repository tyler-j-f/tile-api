import React, {useState} from 'react'
import TokenMergeSelector from "../merge/TokenMergeSelector";
import {useEthers} from "@usedapp/core";
import ConnectButton from "../updateTileNft/ConnectButton";
import MergeTxWrapper from "../merge/MergeTxWrapper";
import TransactionSuccess from "../etc/TransactionSuccess";
import StyledPage from "../styledComponents/StyledPage";
import PageHeader from "../styledComponents/PageHeader";
import {Col, Row} from "react-bootstrap";
import PageSubHeader from "../styledComponents/PageSubHeader";
import AttributesTable from "../view/AttributesTable";

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
    tokenMergeResult: {
      tokenAttributes: {}
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
        <>
          <MergeTxWrapper
              tokenId1={mergeData.token1.tokenId}
              tokenId2={mergeData.token2.tokenId}
              contractAddress={mergeData.contractAddress}
              account={account}
              successCallback={handleSuccessfulTx}
          />
          {
            mergeData.tokenMergeResult !== null && (
              <>
                <PageSubHeader>Token Attributes</PageSubHeader>
                <AttributesTable tokenAttributes={mergeData.tokenMergeResult?.tokenAttributes}/>
              </>
            )
          }
        </>
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
        <Row>
          <Col xs={2} sm={2} md={2} lg={2} xl={2} />
          <Col xs={8} sm={8} md={8} lg={8} xl={8} className="text-center" >
            <PageHeader>Merge TileNFTs</PageHeader>
            {mergeData?.txStatus?.isSuccess && getSuccessfulTx()}
            {!mergeData?.txStatus?.isSuccess && getBody()}
          </Col>
          <Col xs={2} sm={2} md={2} lg={2} xl={2} />
        </Row>
      </StyledPage>
  )
}

export default MergePage
