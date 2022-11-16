import { FunctionComponent, useCallback } from "react";
import { ICook, PizzaState } from "../models";
import { ActionButton, DirectionalHint, IImageStyles, Image, ImageFit, ITooltipHostStyles, TooltipDelay, TooltipHost } from "@fluentui/react";
import { useId } from '@fluentui/react-hooks';
import './Cook.scss';

export interface ICookProps {
  data: ICook,
  stop: () => void;
  resume: () => void;
}

const hostStyles: Partial<ITooltipHostStyles> = {
  root: {
    display: 'inline-block',
    maxWidth: '20vw',
    maxHeight: '20vh'
  }
};

const imageStyles: Partial<IImageStyles> = {
  root: {
    minHeight: '12vh',
    minWidth: '12vw',
    maxWidth: '20vw',
    maxHeight: '20vh'
  }
}

const Cook: FunctionComponent<ICookProps> = (props: ICookProps) => {
  const { data, stop, resume } = props;
  const id = useId('tooltip');
  
  const renderTooltipHandler = useCallback(() => {

    return (
      <div className='cook-info'>
        <span>Cook #{data.id}</span>
        {data.stop && (
          <>
            <span>Resting...</span>
            <ActionButton iconProps={{ iconName: 'PlayResume' }} onClick={resume} text='Resume' />
          </>
        )}
        {data.working && (
          <>
            <span>Working on pizza #{data.pizza.id} {data.pizza.name}</span>
            <span>Status: {data.pizza.pizzaState}</span>
            <span>Pizza creation time: {data.pizzaCreationMinTimeInSec.toFixed(0)}</span>
            <ActionButton iconProps={{iconName: 'CirclePause'}} text='Stop' onClick={stop} />
          </>
        )}
        {!data.stop && !data.working && (
          <span>Waiting...</span>
        )}
      </div>
    );
  }, [data, stop, resume]);

  return (
    <TooltipHost 
      styles={hostStyles}
      tooltipProps={{ onRenderContent: renderTooltipHandler }} 
      id={id} 
      directionalHint={DirectionalHint.bottomCenter}
      delay={TooltipDelay.zero}
    >
      {data.stop && (
        <Image
          styles={imageStyles}
          aria-describedby={id}
          src={require('../assets/sleeping-chef.png')}
          imageFit={ImageFit.centerContain}
        />
      )}
      {data.working && (
        <Image
          styles={imageStyles}
          aria-describedby={id}
          src={require('../assets/chef.png')}
          imageFit={ImageFit.centerContain}
        />
      )}
      {!data.stop && !data.working && (
        <Image
          styles={imageStyles}
          aria-describedby={id}
          src={require('../assets/waiting-chef.png')}
          imageFit={ImageFit.centerContain}
        />
      )}
      
    </TooltipHost>
  );
};

export default Cook;