import ConnectButton from "./ConnectButton";
import MetadataSetContractMethod from "./MetadataSetContractMethod";
import {useEthers} from "@usedapp/core";
import TokenNumberForm from "../view/TokenNumberForm";

const UpdateTileNft = () => {
  const {account} = useEthers();

  return (
      <>
        <TokenNumberForm />
        <ConnectButton />
        {account && <MetadataSetContractMethod />}
      </>
  );
}

export default UpdateTileNft;
