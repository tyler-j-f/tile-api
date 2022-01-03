import { useEthers, useEtherBalance } from "@usedapp/core";
import { formatEther } from "@ethersproject/units";
import {Button} from "react-bootstrap";

export default function ConnectButton() {
  const {activateBrowserWallet, account } = useEthers();
  const etherBalance = useEtherBalance(account);

  function handleConnectWallet() {
    activateBrowserWallet();
  }

  return (account && etherBalance) ? (
      <Button>
        <p>
          Account Balance: {parseFloat(formatEther(etherBalance)).toFixed(3)} ETH
        </p>
      </Button>
  ) : (
      <Button onClick={handleConnectWallet}>
        <p>Connect to a wallet</p>
      </Button>
  );
}
