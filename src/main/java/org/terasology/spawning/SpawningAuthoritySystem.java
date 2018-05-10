/*
 * Copyright 2017 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.spawning;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.entitySystem.entity.EntityManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.prefab.Prefab;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.location.LocationComponent;
import org.terasology.logic.players.LocalPlayer;
import org.terasology.math.geom.Vector3f;
import org.terasology.network.NetworkComponent;
import org.terasology.registry.In;


@RegisterSystem(RegisterMode.AUTHORITY)
public class SpawningAuthoritySystem extends BaseComponentSystem {
    private static final Logger logger = LoggerFactory.getLogger(SpawningAuthoritySystem.class);

    @In
    private EntityManager entityManager;

    private Prefab prefabToSpawn;
    private Vector3f spawnPos;


    /**
     * Spawns the desired Oreon at the location of Portal which sends the event
     */
    @ReceiveEvent
    public void oreonSpawn(OreonSpawnEvent event, EntityRef player) {
        prefabToSpawn = event.getOreonPrefab();
        spawnPos = event.getSpawnPos();

        // spawn the new oreon into the world
        //TODO Resource consuming spawn
        //TODO oreon still spawns mid-air
        EntityRef newOreon = entityManager.create(prefabToSpawn, spawnPos);
        OreonSpawnComponent oreonSpawnComponent = newOreon.getComponent(OreonSpawnComponent.class);
        oreonSpawnComponent.parent = player;
        logger.info("Player " + oreonSpawnComponent.parent.getOwner() + "Spawned a new Oreon of Type : " + prefabToSpawn.toString());
    }

}