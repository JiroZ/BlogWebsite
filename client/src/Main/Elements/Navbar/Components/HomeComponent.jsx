import APIService from '../../../../Services/APIService.jsx'
import {Card, Col, Row} from "react-bootstrap";
import React from 'react'
import './HomeComponent.css'

class HomeComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            homeData: []
        }
    }

    componentDidMount() {
        APIService.getAccessibleData()
            .then(response => {
                this.setState({homeData: response.data})
            }).catch(function (ex) {
            console.log('Response parsing failed. Error: ', ex);
        });


        const category = 'ALL'
        const searchString = 'test'
        const search = [searchString, category]

        APIService.getSearchedData(search).then(response => {
            console.log("Searched data" + response)
        }).catch(err => {
            console.warn("Error while fetching searched data : " + err)
        })
    }

    render() {
        return (
            <>
                <div class=' centered contentContainer'>
                    <div>
                        {
                            <Row xs={1} md={2} className="g-4">
                                {Array.from({length: 1}).map((_, idx) => (
                                    this.state.homeData.map(blogData => {
                                        {
                                            return (
                                                <Col>
                                                    {

                                                        <Card key={blogData.id} className="bg-dark text-white">
                                                            <Card.Img
                                                                src="https://image.shutterstock.com/image-vector/set-100-geometric-shapes-memphis-260nw-1511671634.jpg"
                                                                alt="Card image"/>

                                                            <Card.Title>{blogData.blogTitle}</Card.Title>
                                                            <Card.Text style={{width: 100}}>
                                                                {blogData.data}
                                                            </Card.Text>

                                                            <Card.Footer>
                                                                <Card.Text>{"Added on " + blogData.date.substring(0, 10).replaceAll('-', "/")}</Card.Text>
                                                            </Card.Footer>
                                                        </Card>
                                                    }
                                                </Col>
                                            )
                                        }
                                    })
                                ))}
                            </Row>
                        }
                    </div>
                </div>
            </>
        )
    }
}

export default HomeComponent

