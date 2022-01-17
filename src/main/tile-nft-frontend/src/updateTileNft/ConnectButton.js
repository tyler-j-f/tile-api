import { useEthers, useEtherBalance } from "@usedapp/core";
import { formatEther } from "@ethersproject/units";
import {Button} from "react-bootstrap";
import styled from "styled-components";
import StyledText from "../styledComponents/StyledText";

export default function ConnectButton() {
  const {activateBrowserWallet, account } = useEthers();
  const etherBalance = useEtherBalance(account);

  function handleConnectWallet() {
    activateBrowserWallet();
  }

  return (account && etherBalance) ? (
      <StyledText>
        Logged in account Balance: {parseFloat(formatEther(etherBalance)).toFixed(3)} ETH
      </StyledText>
  ) : (
      <Button onClick={handleConnectWallet} className="styledButton" >
        <p>Connect to a wallet</p>
      </Button>
  );
}
