import {Component} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';
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
