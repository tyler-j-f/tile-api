import TileContract from '../contractsJson/Tile.json'
import {useEthers} from "@usedapp/core";
import {useEffect, useState} from "react";
import MetadataSetTx
  from "./MetadataSetTx";
import {ethers} from "ethers";
import styled from "styled-components";
import StyledText from "../styledComponents/StyledText";

const noop = () => {};

const MetadataSetTxWrapper = ({
  contractAddress = null,
  tokenId = null,
  metadataToUpdate = [],
  metadataToSetIndex,
  successCallback = noop,
  dataToSetGetter = noop,
  loadDataToUpdateRelatedData = noop,
  attributesRegex = '',
  numberOfEntriesToSet = 4,
  account = {}
}
) => {
  const { library: provider } = useEthers()
  const [tileContract, setTileContract] = useState(null);
  const [signer, setSigner] = useState(null);
  const [dataToUpdateRelatedData, setDataToUpdateRelatedData] = useState([]);
  const [tokenOwnerAddress, setTokenOwnerAddress] = useState('');

  useEffect(
      () => {
        loadDataToUpdateRelatedData({
          tokenId,
          attributesRegex,
          metadataToUpdate
        }).then(result => {
          setDataToUpdateRelatedData(result);
        }).then(() => {
          handleProviderAndSigner();
        })
      },
      []
  );

  useEffect(
      () => {
        if (tileContract && tokenOwnerAddress === '') {
          tileContract.ownerOf(tokenId).then(
              result => setTokenOwnerAddress(result)
          );
        };
      },
      [tileContract]
  );
  
  const getShouldRender = () => {
    return tileContract && signer && tokenId && dataToUpdateRelatedData.length === numberOfEntriesToSet && metadataToUpdate.length === numberOfEntriesToSet && getIsTokenOwner()
  }

  const handleProviderAndSigner = () => {
    if (provider) {
      setTileContract(
          new ethers.Contract(contractAddress, TileContract.abi, provider)
      );
      // The MetaMask plugin also allows signing transactions to
      // send ether and pay to change state within the blockchain.
      // For this, you need the account signer...
      setSigner(provider.getSigner());
    }
  }

  const getIsTokenOwner = () => tokenOwnerAddress !== '' && account === tokenOwnerAddress;

  return (
      <>
        {getIsTokenOwner() ? null : <StyledText>Logged in account is not the token owner.</StyledText>}
        {getShouldRender() &&
          <MetadataSetTx
              contract={tileContract}
              tokenId={tokenId}
              dataToSetIndex={metadataToSetIndex}
              dataToSet={dataToSetGetter(metadataToUpdate, dataToUpdateRelatedData)}
              successCallback={successCallback}
          />
        }
      </>
  );
}

export default MetadataSetTxWrapper;
