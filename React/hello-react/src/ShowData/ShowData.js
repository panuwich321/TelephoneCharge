import React, { Component } from 'react';
import Data from '../Data/telCharge.json';

class ShowData extends Component {
  render() {
    return (
      <div>
        <h1>Telephone Charge</h1>
        {Data.map((detail, index)=>{
          return <div>
            <p>Telephone no - {detail.tel}</p>
            <p>Charge - {detail.value} baht</p>
          </div>
        })}
      </div>
    );
  }
}

export default ShowData;
