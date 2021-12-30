import { useEthers, useEtherBalance } from "@usedapp/core";
import { formatEther } from "@ethersproject/units";
import {Button} from "react-bootstrap";
import styled from "styled-components";

export default function ConnectButton() {
  const {activateBrowserWallet, account } = useEthers();
  const etherBalance = useEtherBalance(account);

  function handleConnectWallet() {
    activateBrowserWallet();
  }

  return account ? (
      <Button>
        <p>
          {account && etherBalance && parseFloat(formatEther(etherBalance)).toFixed(3)} ETH
        </p>
      </Button>
  ) : (
      <StyledButton onClick={handleConnectWallet}>
        <p>Connect to a wallet</p>
      </StyledButton>
  );
}


const StyledButton = styled.button`
    margin: 5px;
    padding: 5px;
`;