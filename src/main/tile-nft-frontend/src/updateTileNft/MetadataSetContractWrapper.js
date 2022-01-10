import TileContract from '../contractsJson/Tile.json'
import {useEthers} from "@usedapp/core";
import {useEffect, useState} from "react";
import MetadataSetContract
  from "./MetadataSetContract";
import {ethers} from "ethers";
import loadTokenOwnerAddress from "./tokenDataLoaders/loadTokenOwnerAddress";
import styled from "styled-components";

const noop = () => {};

const MetadataSetContractWrapper = ({
  contractAddress = null,
  tokenId = null,
  metadataToUpdate = [],
  metadataToSetIndex,
  successCallback = noop,
  dataToSetGetter = noop,
  loadDataToUpdateRelatedData = noop,
  attributesRegex = '',
  numberOfEntriesToSet = 4,
  account= {}
}
) => {
  const { library: provider } = useEthers()
  const [tileContract, setTileContract] = useState(null);
  const [signer, setSigner] = useState(null);
  const [dataToUpdateRelatedData, setDataToUpdateRelatedData] = useState({
    contractData: [],
    tokenOwnerAddress: ''
  });

  useEffect(
      () => {
        loadDataToUpdateRelatedData({
          tokenId,
          attributesRegex,
          metadataToUpdate
        }).then(loadDataResult => {
          loadTokenOwnerAddress({tokenId, setDataToUpdateRelatedData}).then(tokenOwnerAddress => {
            setDataToUpdateRelatedData({
              contractData: loadDataResult,
              tokenOwnerAddress: tokenOwnerAddress
            });
          })
        }).then(() => handleProviderAndSigner())
      },
      []
  );

  const getShouldRenderContract = () => {
    return tileContract && signer && tokenId && dataToUpdateRelatedData?.contractData?.length === numberOfEntriesToSet && metadataToUpdate.length === numberOfEntriesToSet
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

  return (
      <>
        {dataToUpdateRelatedData.tokenOwnerAddress !== '' && dataToUpdateRelatedData.tokenOwnerAddress !== account &&
            <StyledText>Logged in account does not own token Id: ${tokenId}!!!</StyledText>
        }
        {getShouldRenderContract() &&
          <MetadataSetContract
              contract={tileContract}
              tokenId={tokenId}
              dataToSetIndex={metadataToSetIndex}
              dataToSet={dataToSetGetter(metadataToUpdate, dataToUpdateRelatedData?.contractData)}
              successCallback={successCallback}
              account={account}
          />
        }
      </>
  );
}

const StyledText =
    styled.p`
    color: white;
    font-weight: bold;
    `;

export default MetadataSetContractWrapper;
