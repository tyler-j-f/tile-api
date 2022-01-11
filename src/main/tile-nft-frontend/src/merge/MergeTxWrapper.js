import TileContract from '../contractsJson/Tile.json'
import {useEthers} from "@usedapp/core";
import {useEffect, useState} from "react";
import {ethers} from "ethers";
import styled from "styled-components";
import MergetTx from "./MergetTx";

const noop = () => {};

const MergeTxWrapper = ({
      contractAddress = null,
      tokenId1 = null,
      tokenId2 = null,
      successCallback = noop,
      account = {}
    }
) => {
  const { library: provider } = useEthers()
  const [tileContract, setTileContract] = useState(null);
  const [signer, setSigner] = useState(null);
  const [tokenOwnerAddresses, setTokenOwnerAddresses] = useState({
    token1Owner: '',
    token2Owner: ''
  });

  useEffect(
      () => {
        handleProviderAndSigner();
      },
      []
  );

  useEffect(
      () => {
        if (tileContract && tokenOwnerAddresses.token1Owner === '') {
          tileContract.ownerOf(tokenId1).then(
              result => setTokenOwnerAddresses({
                token1Owner: result,
                token2Owner: tokenOwnerAddresses.token2Owner
              })
          );
        };
        if (tileContract && tokenOwnerAddresses.token2Owner === '') {
          tileContract.ownerOf(tokenId2).then(
              result => setTokenOwnerAddresses({
                token1Owner: tokenOwnerAddresses.token1Owner,
                token2Owner: result
              })
          );
        };
      },
      [tileContract]
  );

  const getShouldRender = () => {
    return tileContract && signer && tokenId1 && tokenId2 && getIsTokenOwner(tokenOwnerAddresses.token1Owner) && getIsTokenOwner(tokenOwnerAddresses.token2Owner)
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

  const getIsTokenOwner = (address) => {
    return address !== '' && account === address;
  }

  return (
      <>
        {getIsTokenOwner() ? null : <StyledText>Logged in account is not the token owner.</StyledText>}
        {getShouldRender() &&
        <MergetTx
            contract={tileContract}
            tokenId1={tokenId1}
            tokenId2={tokenId2}
            successCallback={successCallback}
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

export default MergeTxWrapper;
