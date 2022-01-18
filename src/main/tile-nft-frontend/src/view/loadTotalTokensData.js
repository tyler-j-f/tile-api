const loadTotalTokensData = () => {
  return fetch(
      `${window.location.origin}/api/contract/getAddress`,
      {method: 'get'}
  )
  .then(response => {
    console.log("loadTotalTokensData response", response);
    if (response.status === 200) {
      return response.json();
    }
    return null;
  }).then(data => {
    console.log("loadTotalTokensData data", data);
  })
}

export default loadTotalTokensData;
