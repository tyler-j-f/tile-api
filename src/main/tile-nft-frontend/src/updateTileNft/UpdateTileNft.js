import {Component} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';
import { Rinkeby } from '@usedapp/core'
import ConnectButton from "./ConnectButton";

class Leaderboard extends Component {

  constructor(props) {
    console.log(Rinkeby);
    super(props);
  }

  componentDidMount() {

  }

  render() {
    return (
        <>
          <ConnectButton />
          <p>Testing the update page</p>
        </>
    );
  }
}

export default Leaderboard;
