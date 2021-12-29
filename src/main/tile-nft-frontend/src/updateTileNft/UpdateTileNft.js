import {Component} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';
import { Rinkeby } from '@usedapp/core'
import ConnectButton from "./ConnectButton";
import MetadataSetContractMethod from "./MetadataSetContractMethod";

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
          <MetadataSetContractMethod />
        </>
    );
  }
}

export default Leaderboard;
