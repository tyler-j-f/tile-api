import TileContract from '../contractsJson/Tile.json'
import {useEthers} from "@usedapp/core";
import {useEffect, useState} from "react";
import {ethers} from "ethers";
import styled from "styled-components";
import MergetTx from "./MergetTx";

const noop = () => {};

const initialTokenOwnerAddressesState = {
  token1: {
    tokenId: '',
    owner: ''
  },
  token2: {
    tokenId: '',
    owner: ''
  }
};

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
  const [tokenOwnerAddresses, setTokenOwnerAddresses] = useState(initialTokenOwnerAddressesState);

  useEffect(
      () => {
        handleProviderAndSigner();
      },
      []
  );

  useEffect(
      () => {
        if (
            tileContract &&
            (
                tokenOwnerAddresses.token1.owner === '' || tokenOwnerAddresses.token2.owner === '' ||
                (tokenOwnerAddresses?.token1?.tokenId !== tokenId1 || tokenOwnerAddresses?.token2?.tokenId !== tokenId2)
            )
        ) {
          tileContract.ownerOf(tokenId1).then(
              token1Owner => tileContract.ownerOf(tokenId2).then(
                  token2Owner => setTokenOwnerAddresses({
                    token1: {
                      tokenId: tokenId1,
                      owner: token1Owner
                    },
                    token2: {
                      tokenId: tokenId2,
                      owner: token2Owner
                    }
                  })
              )
          ).catch(e => {
            console.log("ERROR CAUGHT!!!", e);
            setTokenOwnerAddresses(initialTokenOwnerAddressesState);
          });
        }
      },
      [tileContract, tokenId1, tokenId2]
  );

  const getShouldRender = () => {
    return tileContract && signer && tokenId1 && tokenId2 && getIsOwnerOfBothTokens();
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


  const getIsOwnerOfBothTokens = () => {
    console.log("DEBUG getIsOwnerOfBothTokens", tokenOwnerAddresses, account);
    return getIsTokenOwner(tokenOwnerAddresses.token1.owner) && getIsTokenOwner(tokenOwnerAddresses.token2.owner);
  }

  const getIsTokenOwner = (address) => {
    return address !== '' && account === address;
  }

  return (
      <>
        {getIsTokenOwner() ? null : <StyledText>Logged in account does not own both tokens.</StyledText>}
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