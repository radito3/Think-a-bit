import React from "react";
import { connect } from "react-redux";
import {
    AppBar,
    Drawer,
    IconButton,
} from "material-ui";
import NavigationClose from "material-ui/svg-icons/navigation/close";
import SideMenuOptions from "./side-menu-options.jsx";
import {
    toggleSideMenu,
    openSideMenu,
    closeSideMenu
} from "../store/actions/side-menu";

class SideMenu extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            open: false
        };
    }

    render() {
        return <Drawer
            docked={false}
            open={this.props.sideMenu.open}
            containerStyle={{
                "backgroundColor": "#C8E6C9"
            }}
            overlayStyle={{
                "opacity": "0"
            }}
            onRequestChange={open => {
                if (open) {
                    this.props.openSideMenu();
                } else {
                    this.props.closeSideMenu();
                }
            }}
        >
            <AppBar
                title="Think-a-bit"
                style={{
                    "backgroundColor": "#A1887F"
                }}
                iconElementLeft={<IconButton><NavigationClose /></IconButton>}
                onLeftIconButtonClick={() => this.props.toggleSideMenu()}
                onTitleClick={() => this.props.toggleSideMenu()}
            />
            <SideMenuOptions />
        </Drawer>;
    }
}

export default connect(store => {
    return {
        sideMenu: store.sideMenu
    };
}, {
    toggleSideMenu,
    openSideMenu,
    closeSideMenu
})(SideMenu);