import React from "react";
import { GridList, RaisedButton } from "material-ui";
import StarBorder from "material-ui/svg-icons/toggle/star-border";

class CategoriesPage extends React.Component {
    render() {
        const styles = {
            root: {
                display: 'flex',
                flexWrap: 'wrap',
                justifyContent: 'space-around',
            },
            gridList: {
                width: '100%',
                height: '100%',
                overflowY: 'auto',
            }
        };

        const tilesData = [
            {
                img: 'images/grid-list/00-52-29-429_640.jpg',
                title: 'Breakfast',
                author: 'jill111',
            },
            {
                img: 'images/grid-list/burger-827309_640.jpg',
                title: 'Tasty burger',
                author: 'pashminu',
            },
            {
                img: 'images/grid-list/camera-813814_640.jpg',
                title: 'Camera',
                author: 'Danson67',
            },
            {
                img: 'images/grid-list/morning-819362_640.jpg',
                title: 'Morning',
                author: 'fancycrave1',
            },
            {
                img: 'images/grid-list/hats-829509_640.jpg',
                title: 'Hats',
                author: 'Hans',
            },
            {
                img: 'images/grid-list/honey-823614_640.jpg',
                title: 'Honey',
                author: 'fancycravel',
            },
            {
                img: 'images/grid-list/vegetables-790022_640.jpg',
                title: 'Vegetables',
                author: 'jill111',
            }
        ];

        return <div style={styles.root}>
            <GridList
                cellHeight={180}
                style={styles.gridList}
                cols={4}
                padding={8}
            >
                {tilesData.map((tile) => (
                    <RaisedButton
                    key={tile.img}
                        buttonStyle={{
                            "fontSize": "20px",
                            "textAlign": "center",
                            "margin": "auto"
                        }}
                        style={{
                            "height": "100%",
                            "width": "100%"
                        }}
                    >{tile.title}
                    </RaisedButton>
                ))}
            </GridList>
        </div>;
    }
}

export default CategoriesPage;