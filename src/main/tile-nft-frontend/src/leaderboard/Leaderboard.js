import {Component} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';

class Leaderboard extends Component {
  render() {
    let loadingSymbol = this.state.isLoading ? this.getSpinner() : null;
    let form = this.state.isLoading ? null : this.getForm();
    return this.state.isLoading ? loadingSymbol : form;
  }
}

export default TokenNumberForm;
