// --- Map Initialization ---
function initMap() {
  const SRM_CENTER = [12.8230, 80.0459];
  const map = L.map('map').setView(SRM_CENTER, 16);

  // Base tiles
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; OpenStreetMap contributors'
  }).addTo(map);

  // Icons
  const hostelIcon = L.icon({ iconUrl: "https://cdn-icons-png.flaticon.com/512/3448/3448599.png", iconSize: [30,30] });
  const libraryIcon = L.icon({ iconUrl: "https://cdn-icons-png.flaticon.com/512/29/29302.png", iconSize: [30,30] });
  const canteenIcon = L.icon({ iconUrl: "https://cdn-icons-png.flaticon.com/512/1046/1046784.png", iconSize: [30,30] });

  const locations = [
    { name: "Hostel A", coords: [12.8235, 80.0465], icon: hostelIcon, info: "Capacity: 300 students" },
    { name: "Library", coords: [12.8226, 80.0448], icon: libraryIcon, info: "Open: 8 AM – 8 PM" },
    { name: "Canteen", coords: [12.8242, 80.0453], icon: canteenIcon, info: "Open: 7 AM – 10 PM" },
    { name: "Main Gate", coords: SRM_CENTER, icon: L.icon({ iconUrl: "https://cdn-icons-png.flaticon.com/512/854/854878.png", iconSize: [36,36] }), info: "SRM KTR Main Gate" }
  ];

  let markers = [], routingControl = null, userLatLng = null, userMarker = null;

  // Add markers
  locations.forEach(loc => {
    const popupContent = `<b>${loc.name}</b><br>${loc.info}<br><br>
      <button onclick="routeTo(${loc.coords[0]}, ${loc.coords[1]})">📍 Get Directions</button>`;
    const marker = L.marker(loc.coords, { icon: loc.icon }).addTo(map).bindPopup(popupContent);
    markers.push(marker);
  });

  // Geolocation
  function locateUser() {
    if (!navigator.geolocation) return;
    navigator.geolocation.getCurrentPosition(pos => {
      const lat = pos.coords.latitude;
      const lng = pos.coords.longitude;
      userLatLng = L.latLng(lat, lng);
      if(userMarker) map.removeLayer(userMarker);
      userMarker = L.marker(userLatLng).addTo(map)
        .bindPopup("📍 You are here").openPopup();
      map.setView(userLatLng, 17);
    });
  }
  locateUser();

  // Recenter & Reset
  window.recenterMap = function() {
    if(userLatLng){ map.setView(userLatLng,17); userMarker.openPopup(); }
    else locateUser();
  }
  window.resetMap = function() {
    map.setView(SRM_CENTER,16);
    if(routingControl){ map.removeControl(routingControl); routingControl=null; }
    markers.forEach(m=>m.closePopup());
  }

  // Routing
  window.routeTo = function(lat,lng){
    if(!userLatLng){ alert("Locate yourself first!"); return; }
    const dest = L.latLng(lat,lng);
    if(routingControl) map.removeControl(routingControl);
    routingControl = L.Routing.control({
      waypoints: [userLatLng,dest],
      router: L.Routing.osrmv1({ serviceUrl: 'https://router.project-osrm.org/route/v1' }),
      lineOptions:{ styles:[{color:'blue', weight:5, opacity:0.7}] },
      addWaypoints:false,
      draggableWaypoints:false,
      show:false
    }).addTo(map);
  }

  // Search
  const searchInput = document.getElementById("search-input");
  const suggestionsBox = document.getElementById("suggestions");
  let searchTimeout;
  searchInput.addEventListener("input", () => {
    clearTimeout(searchTimeout);
    const query = searchInput.value.trim();
    if(query.length <3){ suggestionsBox.innerHTML=""; return; }
    searchTimeout = setTimeout(()=>{
      fetch(`https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(query)}&limit=5`)
      .then(res=>res.json())
      .then(data=>{
        suggestionsBox.innerHTML="";
        data.forEach(place=>{
          const div = document.createElement("div");
          div.textContent = place.display_name;
          div.onclick = ()=>{
            const destLatLng = L.latLng(place.lat, place.lon);
            map.setView(destLatLng,17);
            L.marker(destLatLng).addTo(map).bindPopup(place.display_name).openPopup();
            if(userLatLng) routeTo(place.lat,place.lon);
            suggestionsBox.innerHTML="";
            searchInput.value="";
          };
          suggestionsBox.appendChild(div);
        });
      }).catch(()=>{ suggestionsBox.innerHTML="<div style='padding:6px;color:red;'>Error fetching results</div>"; });
    },400);
  });
}