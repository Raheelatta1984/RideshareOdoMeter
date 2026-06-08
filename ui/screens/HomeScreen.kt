package com.rideshare.odometer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rideshare.odometer.data.TripEntity
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen() {
        var showStartTripDialog by remember { mutableStateOf(false) }
            var currentOdometer by remember { mutableStateOf("") }
                var selectedPurpose by remember { mutableStateOf(TripEntity.PURPOSE_BUSINESS) }
                    var location by remember { mutableStateOf("") }

                        Scaffold(
                                    topBar = {
                                                    TopAppBar(
                                                                        title = { Text("Rideshare OdoMeter") },
                                                                                        colors = TopAppBarDefaults.topAppBarColors(
                                                                                                                containerColor = MaterialTheme.colorScheme.primaryContainer
                                                                                        )
                                                    )
                                    },
                                            floatingActionButton = {
                                                            FloatingActionButton(onClick = { showStartTripDialog = true }) {
                                                                                Icon(Icons.Default.Add, contentDescription = "Start Trip")
                                                            }
                                            }
                        ) { paddingValues ->
                                Column(
                                                modifier = Modifier
                                                                .fillMaxSize()
                                                                                .padding(paddingValues)
                                                                                                .padding(16.dp),
                                                                                                            horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                                // Dashboard Stats
                                                            Card(
                                                                                modifier = Modifier
                                                                                                    .fillMaxWidth()
                                                                                                                        .padding(bottom = 24.dp)
                                                            ) {
                                                                                Column(
                                                                                                        modifier = Modifier.padding(16.dp),
                                                                                                                            horizontalAlignment = Alignment.CenterHorizontally
                                                                                ) {
                                                                                                        Text(
                                                                                                                                    text = "This Month",
                                                                                                                                                            style = MaterialTheme.typography.titleMedium
                                                                                                        )
                                                                                                                            Spacer(modifier = Modifier.height(8.dp))
                                                                                                                                                Text(
                                                                                                                                                                            text = "0 km",
                                                                                                                                                                                                    fontSize = 48.sp,
                                                                                                                                                                                                                            fontWeight = FontWeight.Bold,
                                                                                                                                                                                                                                                    color = MaterialTheme.colorScheme.primary
                                                                                                                                                )
                                                                                                                                                                    Text(
                                                                                                                                                                                                text = "Business • Personal",
                                                                                                                                                                                                                        style = MaterialTheme.typography.bodyMedium
                                                                                                                                                                    )
                                                                                }
                                                            }

                                                                        // Quick Actions
                                                                                    Text(
                                                                                                        text = "Quick Actions",
                                                                                                                        style = MaterialTheme.typography.titleLarge,
                                                                                                                                        modifier = Modifier
                                                                                                                                                            .align(Alignment.Start)
                                                                                                                                                                                .padding(bottom = 12.dp)
                                                                                    )

                                                                                                Row(
                                                                                                                    modifier = Modifier.fillMaxWidth(),
                                                                                                                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                                                                                ) {
                                                                                                                    ActionCard(
                                                                                                                                            title = "Start Trip",
                                                                                                                                                                icon = Icons.Default.PlayArrow,
                                                                                                                                                                                    onClick = { showStartTripDialog = true }
                                                                                                                    )
                                                                                                                                    ActionCard(
                                                                                                                                                            title = "History",
                                                                                                                                                                                icon = Icons.Default.History,
                                                                                                                                                                                                    onClick = { /* TODO: Navigate to history */ }
                                                                                                                                    )
                                                                                                }

                                                                                                            Spacer(modifier = Modifier.height(32.dp))

                                                                                                                        Text(
                                                                                                                                            text = "ATO Logbook Ready",
                                                                                                                                                            style = MaterialTheme.typography.bodyMedium,
                                                                                                                                                                            color = MaterialTheme.colorScheme.secondary
                                                                                                                        )
                                }
                        }

                            // Start Trip Dialog
                                if (showStartTripDialog) {
                                            StartTripDialog(
                                                            onDismiss = { showStartTripDialog = false },
                                                                        onStartTrip = { startKm, purpose, loc ->
                                                                                        // TODO: Save to database via ViewModel
                                                                                                        val newTrip = TripEntity(
                                                                                                                                date = LocalDate.now(),
                                                                                                                                                    timeStart = LocalTime.now(),
                                                                                                                                                                        startKm = startKm,
                                                                                                                                                                                            endKm = startKm, // Will update later
                                                                                                                                                                                                                purpose = purpose,
                                                                                                                                                                                                                                    location = loc
                                                                                                        )
                                                                                                                        // For now just log it
                                                                                                                                        println("New Trip Started: $newTrip")
                                                                                                                                                        showStartTripDialog = false
                                                                        }
                                            )
                                }
}

@Composable
fun ActionCard(
        title: String,
            icon: androidx.compose.ui.graphics.vector.ImageVector,
                onClick: () -> Unit
) {
        Card(
                    modifier = Modifier
                                .weight(1f)
                                            .height(120.dp),
                                                    onClick = onClick
        ) {
                    Column(
                                    modifier = Modifier
                                                    .fillMaxSize()
                                                                    .padding(16.dp),
                                                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                                                            verticalArrangement = Arrangement.Center
                    ) {
                                    Icon(icon, contentDescription = null, modifier = Modifier.size(40.dp))
                                                Spacer(modifier = Modifier.height(8.dp))
                                                            Text(title, fontWeight = FontWeight.Medium)
                    }
        }
}

@Composable
fun StartTripDialog(
        onDismiss: () -> Unit,
            onStartTrip: (Double, String, String) -> Unit
) {
        var startKm by remember { mutableStateOf("") }
            var purpose by remember { mutableStateOf(TripEntity.PURPOSE_BUSINESS) }
                var location by remember { mutableStateOf("") }

                    AlertDialog(
                                onDismissRequest = onDismiss,
                                        title = { Text("Start New Trip") },
                                                text = {
                                                                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                                                                    OutlinedTextField(
                                                                                                            value = startKm,
                                                                                                                                onValueChange = { startKm = it },
                                                                                                                                                    label = { Text("Current Odometer Reading (km)") },
                                                                                                                                                                        keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                                                                                    )

                                                                                                    OutlinedTextField(
                                                                                                                            value = location,
                                                                                                                                                onValueChange = { location = it },
                                                                                                                                                                    label = { Text("Current Location (Suburb)") }
                                                                                                    )

                                                                                                                    Row {
                                                                                                                                            Text("Purpose:", modifier = Modifier.align(Alignment.CenterVertically))
                                                                                                                                                                Spacer(modifier = Modifier.width(8.dp))
                                                                                                                                                                                    ToggleButtonGroup(
                                                                                                                                                                                                                selected = purpose,
                                                                                                                                                                                                                                        onSelectionChanged = { purpose = it }
                                                                                                                                                                                    )
                                                                                                                    }
                                                                }
                                                },
                                                        confirmButton = {
                                                                        Button(
                                                                                            onClick = {
                                                                                                                    val km = startKm.toDoubleOrNull() ?: 0.0
                                                                                                                                        onStartTrip(km, purpose, location)
                                                                                            }
                                                                        ) {
                                                                                            Text("Start Trip")
                                                                        }
                                                        },
                                                                dismissButton = {
                                                                                TextButton(onClick = onDismiss) {
                                                                                                    Text("Cancel")
                                                                                }
                                                                }
                    )
}

@Composable
fun ToggleButtonGroup(
        selected: String,
            onSelectionChanged: (String) -> Unit
) {
        Row {
                    FilterChip(
                                    selected = selected == TripEntity.PURPOSE_BUSINESS,
                                                onClick = { onSelectionChanged(TripEntity.PURPOSE_BUSINESS) },
                                                            label = { Text("Business") }
                    )
                            Spacer(modifier = Modifier.width(8.dp))
                                    FilterChip(
                                                    selected = selected == TripEntity.PURPOSE_PERSONAL,
                                                                onClick = { onSelectionChanged(TripEntity.PURPOSE_PERSONAL) },
                                                                            label = { Text("Personal") }
                                    )
        }
}
                                    )
                    )
        }
}
)
                                                                                }
                                                                }
                                                                        }
                                                                                            }
                                                                        )
                                                        }
                                                                                                                                                                                    )
                                                                                                                    }
                                                                                                    )
                                                                                    )
                                                                }
                                                }
                    )
}
)
                    }
                    )
        }
        )
}
)
                                                                                                        )}
                                            )
                                }
                                                                                                                        )
                                                                                                                                    )
                                                                                                                    )
                                                                                                }
                                                                                                )
                                                                                    )
                                                                                                                                                                    )
                                                                                                                                                )
                                                                                                        )
                                                                                }
                                                                                )
                                                            }
                                                            )
                                }
                                )}
                                                            }
                                            }
                                                                                        )
                                                    )
                                    }
                        )
}