export const environment = {
  production: false,
  // BASE_CHAT_URI: 'ws://localhost:8080/with-stomp/chat' // websocket-with-stomp without load balancing and without relay
  // BASE_CHAT_URI: 'ws://localhost:80/with-stomp/chat' // websocket-with-stomp with load balancing and without relay
  // BASE_CHAT_URI: 'ws://localhost:8080/with-stomp/chat' // websocket-with-stomp without load balancing and relay
  BASE_CHAT_URI: 'ws://localhost:80/with-stomp/chat?access_token=...' // websocket-with-stomp with load balancing and relay
  // BASE_CHAT_URI: 'ws://localhost:80/with-stomp/chat' // websocket-with-stomp without load balancing and relay failover
  // BASE_CHAT_URI: 'ws://localhost:80/with-stomp/chat' // websocket-with-stomp with load balancing and relay failover
};
