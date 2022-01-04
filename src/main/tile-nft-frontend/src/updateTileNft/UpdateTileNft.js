import ConnectButton from "./ConnectButton";
import MetadataSetContractWrapper from "./MetadataSetContractWrapper";
import {useEthers} from "@usedapp/core";
import ViewToken from "../view/ViewToken";
import {useState} from "react";

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
              {account && <MetadataSetContractWrapper />}
            </>
        )}
      </>
  );
}

export default UpdateTileNft;
