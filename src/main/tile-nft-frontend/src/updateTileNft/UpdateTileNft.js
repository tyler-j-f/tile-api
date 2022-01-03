import ConnectButton from "./ConnectButton";
import MetadataSetContractMethod from "./MetadataSetContractMethod";
import {useEthers} from "@usedapp/core";
import TokenNumberForm from "../view/TokenNumberForm";
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
        <TokenNumberForm tokenLoadedCallback={handleTokenLoaded} />
        {tokenId !== '' && (
            <>
              <ConnectButton />
              {account && <MetadataSetContractMethod />}
            </>
        )}
      </>
  );
}

export default UpdateTileNft;
