import { DefaultButton, Dialog, DialogFooter, DialogType, Dropdown, IDropdownOption, PrimaryButton, Stack, TextField } from "@fluentui/react";
import React, { Dispatch, SetStateAction } from "react";

export interface IConfigProps {
    isHidden: boolean,
    hiddenChanger: Dispatch<SetStateAction<boolean>>,
}

const Config: React.FunctionComponent<IConfigProps> = (props: IConfigProps) => {

    const dialogContentProps = {
        type: DialogType.normal,
        title: 'Pizzeria config',
        closeButtonAriaLabel: 'Close',
        subText: 'Here you can config your pizzeria.',
    };

    const handleFooterClick = (sendRequest: boolean) => {
        if (sendRequest) {
            //reques logic goes here
        }
        props.hiddenChanger(true);


    }

    const pizzaStrategiesOptions : IDropdownOption[] = 
    [
        {key: 'alone', text: "One cook - fulpizza" },
        {key: 'part', text: "One cook - on process"}
    ]

    const clientStrategiesOptions = [
        {key: 'default', text: "Fixed Genertion" },
        {key: 'random', text: "Random Generation"}
    ]

    return (
        <Dialog
            hidden={props.isHidden}
            dialogContentProps={dialogContentProps}>
            <Stack>
                
                <TextField suffix="pcs" label="Pay desks number" type="number"></TextField>
                <TextField suffix="pcs" label="Cooks Number" type="number"></TextField>
                <TextField suffix="pcs" label="Minimal pizzas number" type="number"></TextField>
                <TextField suffix="sec" label="Minimal pizza cooking time" type="number"></TextField>
                <Dropdown label="Pizza cooking strategy" placeholder="Select strategy" options={pizzaStrategiesOptions}></Dropdown>
                <Dropdown label="Client generation strategy" placeholder="Select strategy" options={clientStrategiesOptions}></Dropdown>
            </Stack>
            <DialogFooter>
                <PrimaryButton onClick={() => handleFooterClick(true)}>Save</PrimaryButton>
                <DefaultButton onClick={() => handleFooterClick(false)}>Cancel</DefaultButton>
            </DialogFooter>
        </Dialog>
    );
}

export default Config;