import {useState} from 'react';
import APIService from '../../../../Services/APIService.jsx'
import {Card} from "react-bootstrap";

const HomeComponent = () => {
    let data = useState(Array)
    APIService.getAccessibleData().then(response => {
        console.log(response.data)
        data = response.data
    })

    const category = 'ALL'
    const searchString = 'test'
    const search = [searchString, category]

    APIService.getSearchedData(search).then(response => {
        console.log("Searched data" + response)
    }).catch(err => {
        console.warn("Error while fetching searched data : " + err)
    })

    return (
        <>
            <div
                style={{
                    display: 'flex',
                    justifyContent: 'Center',
                    alignItems: 'Right',
                    height: '10vh'
                }}
            >

            </div>

            {
                data.map(blogData => {
                    return (
                        <Card key={blogData.id} className="bg-dark text-white">
                            <Card.Img src="holder.js/100px270" alt="Card image" />
                            <Card.ImgOverlay>
                                <Card.Title>Card title</Card.Title>
                                <Card.Text>
                                    This is a wider card with supporting text below as a natural lead-in to
                                    additional content. This content is a little bit longer.
                                </Card.Text>
                                <Card.Text>Last updated 3 mins ago</Card.Text>
                            </Card.ImgOverlay>
                        </Card>
                    )
                })
            }
        </>
    )
}
export default HomeComponent

