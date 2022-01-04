import ConnectButton from "./ConnectButton";
import MetadataSetContractWrapper from "./MetadataSetContractWrapper";
import {useEthers} from "@usedapp/core";
import ViewToken from "../view/ViewToken";
import {useState} from "react";
import ColorSelector from "./ColorSelector";

const UpdateTileNft = () => {
  const {account} = useEthers();
  const [tokenId, setTokenId] = useState('');

  const handleTokenLoaded = (tokenId) => {
    if (tokenId) {
      setTokenId(tokenId);
      console.log("Token id found in callback", tokenId)
    }
  }

  return (
      <>
        <ViewToken tokenLoadedCallback={handleTokenLoaded} />
        {tokenId !== '' && (
            <>
              <ConnectButton />
              {account &&
                <>
                  <ColorSelector />
                  <MetadataSetContractWrapper
                      tokenId={tokenId}
                      contractAddress={CONTRACT_ADDRESS}
                      dataToSet={DATA_TO_SET}
                      dataToSetIndex={DATA_TO_SET_INDEX}
                  />
                </>
              }
            </>
        )}
      </>
  );
}

export const DATA_TO_SET_INDEX = 0;
export const DATA_TO_SET = "0x0000111000022200003330000444000000000000000000000000000000000000";
export const CONTRACT_ADDRESS     = "0xEc9547ABc4a8c24B99226BeE239c6E29814903Cd";

export default UpdateTileNft;
