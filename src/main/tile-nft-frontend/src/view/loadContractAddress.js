const loadContractAddress = () => {
  return fetch(
      `${window.location.origin}/api/contract/getAddress`,
      {method: 'get'}
  )
  .then(response => {
    if (response.status === 200) {
      return response.json();
    }
    return null;
  })
  .then(json => {
    console.log("ViewToken loadContractAddress json found", json);
    if (json === null) {
      let errorMessage = 'loadContractAddress json is null';
      console.log(errorMessage);
      throw errorMessage;
    }
    return json
  })
  .catch(err => {
    console.log("Error caught!!!", err);
  });
}

export default loadContractAddress;
