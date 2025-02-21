
import React from 'react'

const FooterComponent = () => {
  return (
    <div>
        <footer className='footer' style={{
          
          position: "fixed", 
          bottom: "0", 
          width: "100%", 
          height: "50px", 
          color: "white", 
          backgroundColor:"black",
          textAlign:"center"
          
          }}>
            <span>All rights reserved 2023 by vleven</span>
        </footer>
    </div>
  )
}

export default FooterComponent