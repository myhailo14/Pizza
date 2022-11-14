import CashDeskSection from "./components/CashDeskSection/CashDeskSection";
import { DefaultButton, DocumentCard, PrimaryButton, registerIcons } from "@fluentui/react";
import { PlayResumeIcon, CirclePauseIcon } from '@fluentui/react-icons-mdl2';
import Kitchen from "./components/Kitchen";
import './App.scss';
import { useCallback, useEffect, useState } from "react";
import { Cooks } from "./models";

registerIcons({
  icons: {
    PlayResume: <PlayResumeIcon />,
    CirclePause: <CirclePauseIcon />
  }
});

let interval: NodeJS.Timeout;

const App = () => {
  const [isPizzeriaWorking, setIsPizzeriaWorking] = useState<boolean>(false);
  const [cooks, setCooks] = useState<Cooks | null>(null);
  const [queues, setQueues] = useState([]);

  function getQueues() {
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

  useEffect(() => {
    if (!isPizzeriaWorking) {
      clearInterval(interval);
      return;
    }

    interval = setInterval(async () => {
      const response = await fetch('http://localhost:8080/cooks');
      const cooks = (await response.json()) as Cooks;
      setCooks(cooks);
    }, 100);
  }, [isPizzeriaWorking]);

  const stopCook = useCallback((id: number) => {
    fetch(`http://localhost:8080/stop-cook/${id}`, {
      method: 'PUT'
    });
  }, []);

  const resumeCook = useCallback((id: number) => {
    fetch(`http://localhost:8080/resume-cook/${id}`, {
      method: 'PUT'
    });
  }, []);

  return (
    <>
      <DocumentCard className='app-wrapper'>
        <CashDeskSection queues={queues} /> 
        <Kitchen cooks={cooks ?? []} stopCook={stopCook} resumeCook={resumeCook} />
        <DocumentCard className='buttons-container'>
          <DefaultButton text='Configuration' />
          <PrimaryButton text='Start' />
          <PrimaryButton text='Stop' />
        </DocumentCard>
      </DocumentCard>

      {/* <button type="button" onClick={getQueues} /> */}
    </>
  );
}

export default App;
