import React from 'react'
import styled from 'styled-components';
import ViewToken from "../view/ViewToken";

const noop = () => {};

const TokenMergeSelector = ({
  tokenLoadedCallback = noop,
  headerText = ''
}) => {
  return (
      <>
        <StyledText>{headerText}</StyledText>
        <ViewToken
            tokenLoadedCallback={tokenLoadedCallback}
        />
      </>
  )
}

const StyledText =
    styled.p`
    color: white;
    font-weight: bold;
    `;

export default TokenMergeSelector
