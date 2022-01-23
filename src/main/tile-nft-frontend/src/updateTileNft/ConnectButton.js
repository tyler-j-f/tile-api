import { useEthers, useEtherBalance } from "@usedapp/core";
import { formatEther } from "@ethersproject/units";
import {Button} from "react-bootstrap";
import StyledText from "../styledComponents/StyledText";
import {useEffect} from "react";

const noop = () => {};

export default function ConnectButton({
    connectToWalletCallback = noop
}) {
  const {activateBrowserWallet, account} = useEthers();
  const etherBalance = useEtherBalance(account);

  function handleConnectWallet() {
    activateBrowserWallet();
  }

  useEffect(
      () => {
        if (!!account) {
          connectToWalletCallback(account);
        }
      },
      [account]
  );

  return (account && etherBalance) ? (
      <StyledText className="addMargin5" >
        Logged in account Balance: {parseFloat(formatEther(etherBalance)).toFixed(3)} ETH
      </StyledText>
  ) : (
      <Button onClick={handleConnectWallet} className="styledButton text-nowrap addMarginRight60" >
        <p>Connect to a wallet</p>
      </Button>
  );
}
