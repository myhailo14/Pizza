import { useEffect, useState } from "react";
import CashDeskSection from "./components/CashDeskSection/CashDeskSection";
const App = () => {
  const [queues, setQueues] = useState([]);
  function getQueues(){
    let interval = setInterval(async () => {
      let response = await fetch("http://localhost:8080/queues");
      if (response.ok) { 
        let queuesArr = await response.json();
        setQueues(queuesArr);
      } else {
        alert("Error HTTP: " + response.status);
      }
    }, 1000)
  }
  return (
    <>
      <CashDeskSection queues={queues} />
      <button type="button" onClick={getQueues} />
    </>
  );
}

export default App;
