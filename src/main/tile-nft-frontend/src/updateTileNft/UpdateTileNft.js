import ConnectButton from "./ConnectButton";
import MetadataSetContractMethod from "./MetadataSetContractMethod";
import {useEthers} from "@usedapp/core";

const UpdateTileNft = () => {
  const {account} = useEthers();

  return (
      <>
        <ConnectButton />
        {account && <MetadataSetContractMethod />}
      </>
  );
}

export default UpdateTileNft;
