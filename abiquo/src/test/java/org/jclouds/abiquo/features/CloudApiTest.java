/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jclouds.abiquo.features;

import static org.jclouds.abiquo.domain.DomainUtils.withHeader;
import static org.jclouds.reflect.Reflection2.method;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.abiquo.domain.CloudResources;
import org.jclouds.abiquo.domain.EnterpriseResources;
import org.jclouds.abiquo.domain.InfrastructureResources;
import org.jclouds.abiquo.domain.NetworkResources;
import org.jclouds.abiquo.domain.cloud.options.VirtualDatacenterOptions;
import org.jclouds.abiquo.domain.cloud.options.VirtualMachineOptions;
import org.jclouds.abiquo.domain.cloud.options.VirtualMachineTemplateOptions;
import org.jclouds.abiquo.domain.cloud.options.VolumeOptions;
import org.jclouds.abiquo.domain.network.options.IpOptions;
import org.jclouds.abiquo.domain.options.search.reference.OrderBy;
import org.jclouds.abiquo.fallbacks.MovedVolume;
import org.jclouds.abiquo.functions.ReturnTaskReferenceOrNull;
import org.jclouds.http.functions.ParseXMLWithJAXB;
import org.jclouds.http.functions.ReleasePayloadAndReturn;
import org.jclouds.http.functions.ReturnStringIf2xx;
import org.jclouds.reflect.Invocation;
import org.jclouds.rest.internal.GeneratedHttpRequest;
import org.testng.annotations.Test;

import com.abiquo.model.enumerator.HypervisorType;
import com.abiquo.model.rest.RESTLink;
import com.abiquo.model.transport.AcceptedRequestDto;
import com.abiquo.model.transport.LinksDto;
import com.abiquo.server.core.appslibrary.VirtualMachineTemplateDto;
import com.abiquo.server.core.appslibrary.VirtualMachineTemplatesDto;
import com.abiquo.server.core.cloud.VirtualApplianceDto;
import com.abiquo.server.core.cloud.VirtualApplianceStateDto;
import com.abiquo.server.core.cloud.VirtualAppliancesDto;
import com.abiquo.server.core.cloud.VirtualDatacenterDto;
import com.abiquo.server.core.cloud.VirtualDatacentersDto;
import com.abiquo.server.core.cloud.VirtualMachineDto;
import com.abiquo.server.core.cloud.VirtualMachineStateDto;
import com.abiquo.server.core.cloud.VirtualMachineTaskDto;
import com.abiquo.server.core.cloud.VirtualMachineWithNodeExtendedDto;
import com.abiquo.server.core.cloud.VirtualMachinesWithNodeExtendedDto;
import com.abiquo.server.core.enterprise.EnterpriseDto;
import com.abiquo.server.core.infrastructure.DatacenterDto;
import com.abiquo.server.core.infrastructure.network.PrivateIpDto;
import com.abiquo.server.core.infrastructure.network.PrivateIpsDto;
import com.abiquo.server.core.infrastructure.network.PublicIpDto;
import com.abiquo.server.core.infrastructure.network.PublicIpsDto;
import com.abiquo.server.core.infrastructure.network.VLANNetworkDto;
import com.abiquo.server.core.infrastructure.network.VLANNetworksDto;
import com.abiquo.server.core.infrastructure.network.VMNetworkConfigurationsDto;
import com.abiquo.server.core.infrastructure.storage.DiskManagementDto;
import com.abiquo.server.core.infrastructure.storage.DisksManagementDto;
import com.abiquo.server.core.infrastructure.storage.MovedVolumeDto;
import com.abiquo.server.core.infrastructure.storage.TierDto;
import com.abiquo.server.core.infrastructure.storage.TiersDto;
import com.abiquo.server.core.infrastructure.storage.VolumeManagementDto;
import com.abiquo.server.core.infrastructure.storage.VolumesManagementDto;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.Invokable;

/**
 * Tests annotation parsing of {@code CloudApi}
 * 
 * @author Ignasi Barrera
 * @author Francesc Montserrat
 */
@Test(groups = "unit", testName = "CloudApiTest")
public class CloudApiTest extends BaseAbiquoApiTest<CloudApi> {
   /*********************** Virtual Datacenter ***********************/

   public void testListVirtualDatacentersParams() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listVirtualDatacenters", VirtualDatacenterOptions.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(VirtualDatacenterOptions.builder().datacenterId(1).enterpriseId(1).build())));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters?datacenter=1&enterprise=1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualDatacentersDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testListVirtualDatacentersNoParams() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listVirtualDatacenters", VirtualDatacenterOptions.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(VirtualDatacenterOptions.builder().build())));

      assertRequestLineEquals(request, "GET http://localhost/api/cloud/virtualdatacenters HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualDatacentersDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testCreateVirtualDatacenter() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "createVirtualDatacenter", VirtualDatacenterDto.class,
            DatacenterDto.class, EnterpriseDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method, ImmutableList.<Object> of(
            CloudResources.virtualDatacenterPost(), InfrastructureResources.datacenterPut(),
            EnterpriseResources.enterprisePut())));

      assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters?datacenter=1&enterprise=1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualDatacenterDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(CloudResources.virtualDatacenterPostPayload()),
            VirtualDatacenterDto.class, VirtualDatacenterDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testGetVirtualDatacenter() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "getVirtualDatacenter", Integer.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method, ImmutableList.<Object> of(1)));

      assertRequestLineEquals(request, "GET http://localhost/api/cloud/virtualdatacenters/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualDatacenterDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, NullOnNotFoundOr404.class);

      checkFilters(request);
   }

   public void testUpdateVirtualDatacenter() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "updateVirtualDatacenter", VirtualDatacenterDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut())));

      assertRequestLineEquals(request, "PUT http://localhost/api/cloud/virtualdatacenters/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualDatacenterDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(CloudResources.virtualDatacenterPutPayload()),
            VirtualDatacenterDto.class, VirtualDatacenterDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testDeleteVirtualDatacenter() throws SecurityException, NoSuchMethodException {
      Invokable<?, ?> method = method(CloudApi.class, "deleteVirtualDatacenter", VirtualDatacenterDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut())));

      assertRequestLineEquals(request, "DELETE http://localhost/api/cloud/virtualdatacenters/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   /*********************** Ips ***********************/

   public void testListAvailablePublicIpsWithOptions() throws SecurityException, NoSuchMethodException, IOException {
      IpOptions options = IpOptions.builder().limit(5).build();
      Invokable<?, ?> method = method(CloudApi.class, "listAvailablePublicIps", VirtualDatacenterDto.class,
            IpOptions.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut(), options)));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/publicips/topurchase?limit=5 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + PublicIpsDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testListPurchasedPublicIpsWithOptions() throws SecurityException, NoSuchMethodException, IOException {
      IpOptions options = IpOptions.builder().limit(5).build();
      Invokable<?, ?> method = method(CloudApi.class, "listPurchasedPublicIps", VirtualDatacenterDto.class,
            IpOptions.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut(), options)));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/publicips/purchased?limit=5 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + PublicIpsDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testPurchasePublicIp() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "purchasePublicIp", PublicIpDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(NetworkResources.publicIpToPurchase())));

      assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/5/publicips/purchased/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + PublicIpDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testReleasePublicIp() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "releasePublicIp", PublicIpDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(NetworkResources.publicIpToRelease())));

      assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/5/publicips/topurchase/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + PublicIpDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   /*********************** Available templates ***********************/

   public void testListAvailableTemplates() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listAvailableTemplates", VirtualDatacenterDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut())));

      assertRequestLineEquals(request, "GET http://localhost/api/cloud/virtualdatacenters/1/action/templates HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualMachineTemplatesDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testListAvailableTemplatesWithOptions() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listAvailableTemplates", VirtualDatacenterDto.class,
            VirtualMachineTemplateOptions.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(
            method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut(), VirtualMachineTemplateOptions.builder()
                  .hypervisorType(HypervisorType.XENSERVER).categoryName("Firewalls").idTemplate(1).build())));

      assertRequestLineEquals(request, "GET http://localhost/api/cloud/virtualdatacenters/1/action/templates"
            + "?hypervisorTypeName=XENSERVER&categoryName=Firewalls&idTemplate=1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualMachineTemplatesDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   /*********************** Storage Tiers ***********************/

   public void testListStorageTiers() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listStorageTiers", VirtualDatacenterDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut())));

      assertRequestLineEquals(request, "GET http://localhost/api/cloud/virtualdatacenters/1/tiers HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + TiersDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testGetStorageTier() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "getStorageTier", VirtualDatacenterDto.class, Integer.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut(), 1)));

      assertRequestLineEquals(request, "GET http://localhost/api/cloud/virtualdatacenters/1/tiers/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + TierDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, NullOnNotFoundOr404.class);

      checkFilters(request);
   }

   public void testGetDefaultNetwork() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "getDefaultNetwork", VirtualDatacenterDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut())));

      assertRequestLineEquals(request, "GET http://localhost/api/cloud/virtualdatacenters/1/privatenetworks/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VLANNetworkDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testSetDefaultNetworkInternal() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "setDefaultNetwork", VirtualDatacenterDto.class,
            VLANNetworkDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut(), NetworkResources.privateNetworkPut())));

      RESTLink netLink = NetworkResources.privateNetworkPut().getEditLink();

      assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/action/defaultvlan HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "");
      assertPayloadEquals(request, withHeader("<links><link href=\"" + netLink.getHref()
            + "\" rel=\"internalnetwork\"/></links>"), LinksDto.class, LinksDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testSetDefaultNetworkExternal() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "setDefaultNetwork", VirtualDatacenterDto.class,
            VLANNetworkDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut(), NetworkResources.externalNetworkPut())));

      RESTLink netLink = NetworkResources.externalNetworkPut().getEditLink();

      assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/action/defaultvlan HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "");
      assertPayloadEquals(request, withHeader("<links><link href=\"" + netLink.getHref()
            + "\" rel=\"externalnetwork\"/></links>"), LinksDto.class, LinksDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   /*********************** Private Network ***********************/

   public void testListPrivateNetworks() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listPrivateNetworks", VirtualDatacenterDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut())));

      assertRequestLineEquals(request, "GET http://localhost/api/cloud/virtualdatacenters/1/privatenetworks HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VLANNetworksDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testGetPrivateNetwork() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "getPrivateNetwork", VirtualDatacenterDto.class, Integer.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut(), 1)));

      assertRequestLineEquals(request, "GET http://localhost/api/cloud/virtualdatacenters/1/privatenetworks/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VLANNetworkDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, NullOnNotFoundOr404.class);

      checkFilters(request);
   }

   public void testCreatePrivateNetwork() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "createPrivateNetwork", VirtualDatacenterDto.class,
            VLANNetworkDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut(), NetworkResources.vlanPost())));

      assertRequestLineEquals(request, "POST http://localhost/api/cloud/virtualdatacenters/1/privatenetworks HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VLANNetworkDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(NetworkResources.vlanNetworkPostPayload()), VLANNetworkDto.class,
            VLANNetworkDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testUpdatePrivateNetwork() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "updatePrivateNetwork", VLANNetworkDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(NetworkResources.privateNetworkPut())));

      assertRequestLineEquals(request, "PUT http://localhost/api/cloud/virtualdatacenters/1/privatenetworks/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VLANNetworkDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(NetworkResources.privateNetworkPutPayload()), VLANNetworkDto.class,
            VLANNetworkDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testDeletePrivateNetwork() throws SecurityException, NoSuchMethodException {
      Invokable<?, ?> method = method(CloudApi.class, "deletePrivateNetwork", VLANNetworkDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(NetworkResources.privateNetworkPut())));

      assertRequestLineEquals(request,
            "DELETE http://localhost/api/cloud/virtualdatacenters/1/privatenetworks/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   /*********************** Private Network IPs ***********************/

   public void testListPrivateNetworkIps() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listPrivateNetworkIps", VLANNetworkDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(NetworkResources.privateNetworkPut())));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/privatenetworks/1/ips HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + PrivateIpsDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testListPrivateNetworkIpsWithOptions() throws SecurityException, NoSuchMethodException, IOException {
      IpOptions options = IpOptions.builder().startWith(10).build();
      Invokable<?, ?> method = method(CloudApi.class, "listPrivateNetworkIps", VLANNetworkDto.class, IpOptions.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(NetworkResources.privateNetworkPut(), options)));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/privatenetworks/1/ips?startwith=10 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + PrivateIpsDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testGetPrivateNetworkIp() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "getPrivateNetworkIp", VLANNetworkDto.class, Integer.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(NetworkResources.privateNetworkPut(), 1)));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/privatenetworks/1/ips/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + PrivateIpDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   /*********************** Virtual Appliance ***********************/

   public void testListVirtualAppliances() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listVirtualAppliances", VirtualDatacenterDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut())));

      assertRequestLineEquals(request, "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualAppliancesDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testGetVirtualAppliance() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "getVirtualAppliance", VirtualDatacenterDto.class, Integer.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut(), 1)));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualApplianceDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, NullOnNotFoundOr404.class);

      checkFilters(request);
   }

   public void testGetVirtualApplianceState() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "getVirtualApplianceState", VirtualApplianceDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualAppliancePut())));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/state HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualApplianceStateDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testCreateVirtualAppliance() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "createVirtualAppliance", VirtualDatacenterDto.class,
            VirtualApplianceDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut(), CloudResources.virtualAppliancePost())));

      assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/virtualappliances HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualApplianceDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(CloudResources.virtualAppliancePostPayload()), VirtualApplianceDto.class,
            VirtualApplianceDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testUpdateVirtualAppliance() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "updateVirtualAppliance", VirtualApplianceDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualAppliancePut())));

      assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualApplianceDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(CloudResources.virtualAppliancePutPayload()), VirtualApplianceDto.class,
            VirtualApplianceDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testDeleteVirtualAppliance() throws SecurityException, NoSuchMethodException {
      Invokable<?, ?> method = method(CloudApi.class, "deleteVirtualAppliance", VirtualApplianceDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualAppliancePut())));

      assertRequestLineEquals(request,
            "DELETE http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testDeployVirtualAppliance() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "deployVirtualAppliance", VirtualApplianceDto.class,
            VirtualMachineTaskDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualAppliancePut(), CloudResources.deployOptions())));

      assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/action/deploy HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + AcceptedRequestDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(CloudResources.deployPayload()), VirtualMachineTaskDto.class,
            VirtualMachineTaskDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testUndeployVirtualAppliance() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "undeployVirtualAppliance", VirtualApplianceDto.class,
            VirtualMachineTaskDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualAppliancePut(), CloudResources.undeployOptions())));

      assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/action/undeploy HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + AcceptedRequestDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(CloudResources.undeployPayload()), VirtualMachineTaskDto.class,
            VirtualMachineTaskDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testGetVirtualAppliancePrice() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "getVirtualAppliancePrice", VirtualApplianceDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualAppliancePut())));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/action/price HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + MediaType.TEXT_PLAIN + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ReturnStringIf2xx.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   /*********************** Virtual Machine ***********************/

   public void testListVirtualMachines() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listVirtualMachines", VirtualApplianceDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualAppliancePut())));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualMachinesWithNodeExtendedDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testListVirtualMachinesWithOptions() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listVirtualMachines", VirtualApplianceDto.class,
            VirtualMachineOptions.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(
            method,
            ImmutableList.<Object> of(CloudResources.virtualAppliancePut(), VirtualMachineOptions.builder()
                  .disablePagination().build())));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines?limit=0 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualMachinesWithNodeExtendedDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testGetVirtualMachine() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "getVirtualMachine", VirtualApplianceDto.class, Integer.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualAppliancePut(), 1)));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualMachineWithNodeExtendedDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, NullOnNotFoundOr404.class);

      checkFilters(request);
   }

   public void testCreateVirtualMachine() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "createVirtualMachine", VirtualApplianceDto.class,
            VirtualMachineWithNodeExtendedDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualAppliancePut(), CloudResources.virtualMachinePost())));

      assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualMachineWithNodeExtendedDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(CloudResources.virtualMachinePostPayload()),
            VirtualMachineWithNodeExtendedDto.class, VirtualMachineWithNodeExtendedDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testUpdateVirtualMachine() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "updateVirtualMachine", VirtualMachineWithNodeExtendedDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualMachinePut())));

      assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + AcceptedRequestDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(CloudResources.virtualMachinePutPayload()),
            VirtualMachineWithNodeExtendedDto.class, VirtualMachineWithNodeExtendedDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ReturnTaskReferenceOrNull.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testUpdateVirtualMachineWithOptions() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "updateVirtualMachine", VirtualMachineWithNodeExtendedDto.class,
            VirtualMachineOptions.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(
            method,
            ImmutableList.<Object> of(CloudResources.virtualMachinePut(), VirtualMachineOptions.builder().force(true)
                  .build())));

      assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1?force=true HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + AcceptedRequestDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(CloudResources.virtualMachinePutPayload()),
            VirtualMachineWithNodeExtendedDto.class, VirtualMachineWithNodeExtendedDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ReturnTaskReferenceOrNull.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testChangeVirtualMachineState() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "changeVirtualMachineState", VirtualMachineDto.class,
            VirtualMachineStateDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualMachinePut(), CloudResources.virtualMachineState())));

      assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/state HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + AcceptedRequestDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(CloudResources.virtualMachineStatePayload()),
            VirtualMachineStateDto.class, VirtualMachineStateDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testDeleteVirtualMachine() throws SecurityException, NoSuchMethodException {
      Invokable<?, ?> method = method(CloudApi.class, "deleteVirtualMachine", VirtualMachineDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualMachinePut())));

      assertRequestLineEquals(request,
            "DELETE http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testGetVirtualMachineState() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "getVirtualMachineState", VirtualMachineDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualMachinePut())));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/state HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualMachineStateDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testDeployVirtualMachine() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "deployVirtualMachine", VirtualMachineDto.class,
            VirtualMachineTaskDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualMachinePut(), CloudResources.deployOptions())));

      assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/action/deploy HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + AcceptedRequestDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(CloudResources.deployPayload()), VirtualMachineTaskDto.class,
            VirtualMachineTaskDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testUndeployVirtualMachine() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "undeployVirtualMachine", VirtualMachineDto.class,
            VirtualMachineTaskDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualMachinePut(), CloudResources.undeployOptions())));

      assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/action/undeploy HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + AcceptedRequestDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(CloudResources.undeployPayload()), VirtualMachineTaskDto.class,
            VirtualMachineTaskDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testRebootVirtualMachine() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "rebootVirtualMachine", VirtualMachineDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualMachinePut())));

      assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/action/reset HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + AcceptedRequestDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testListNetworkConfigurations() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listNetworkConfigurations", VirtualMachineDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualMachinePut())));

      assertRequestLineEquals(
            request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/network/configurations HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VMNetworkConfigurationsDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testSetGatewayNetwork() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "setGatewayNetwork", VirtualMachineDto.class,
            VLANNetworkDto.class);

      VirtualMachineDto vm = CloudResources.virtualMachinePut();
      VLANNetworkDto network = NetworkResources.privateNetworkPut();

      GeneratedHttpRequest request = processor.apply(Invocation.create(method, ImmutableList.<Object> of(vm, network)));

      String configLink = vm.searchLink("configurations").getHref() + "/" + network.getId();

      assertRequestLineEquals(
            request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/network/configurations HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "");
      assertPayloadEquals(request, withHeader("<links><link href=\"" + configLink
            + "\" rel=\"network_configuration\"/></links>"), LinksDto.class, LinksDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   /*********************** Virtual Machine Template ***********************/

   public void testGetVirtualMachineTemplate() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "getVirtualMachineTemplate", VirtualMachineDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualMachinePut())));

      assertRequestLineEquals(request,
            "GET http://localhost/api/admin/enterprises/1/datacenterrepositories/1/virtualmachinetemplates/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VirtualMachineTemplateDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testListAttachedVolumes() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listAttachedVolumes", VirtualMachineDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualMachinePut())));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/storage/volumes HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VolumesManagementDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testDetachAllVolumes() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "detachAllVolumes", VirtualMachineDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualMachinePut())));

      assertRequestLineEquals(request,
            "DELETE http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/storage/volumes HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + AcceptedRequestDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ReturnTaskReferenceOrNull.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testReplaceVolumes() throws SecurityException, NoSuchMethodException, IOException {
      VolumeManagementDto first = CloudResources.volumePut();
      VolumeManagementDto second = CloudResources.volumePut();
      second.getEditLink().setHref(second.getEditLink().getHref() + "second");

      Invokable<?, ?> method = method(CloudApi.class, "replaceVolumes", VirtualMachineDto.class,
            VirtualMachineOptions.class, VolumeManagementDto[].class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(
            method,
            ImmutableList.<Object> of(CloudResources.virtualMachinePut(), VirtualMachineOptions.builder().force(true)
                  .build(), new VolumeManagementDto[] { first, second })));

      String editLink = CloudResources.volumePut().getEditLink().getHref();
      assertRequestLineEquals(
            request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/storage/volumes?force=true HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + AcceptedRequestDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader("<links><link href=\"" + editLink + "\" rel=\"volume\"/><link href=\""
            + editLink + "second\" rel=\"volume\"/></links>"), LinksDto.class, LinksDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ReturnTaskReferenceOrNull.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testListAttachedHardDisks() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listAttachedHardDisks", VirtualMachineDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualMachinePut())));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/storage/disks HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + DisksManagementDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testDetachAllHardDisks() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "detachAllHardDisks", VirtualMachineDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualMachinePut())));

      assertRequestLineEquals(request,
            "DELETE http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/storage/disks HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + AcceptedRequestDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ReturnTaskReferenceOrNull.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testReplaceHardDisks() throws SecurityException, NoSuchMethodException, IOException {
      DiskManagementDto first = CloudResources.hardDiskPut();
      DiskManagementDto second = CloudResources.hardDiskPut();
      second.getEditLink().setHref(second.getEditLink().getHref() + "second");

      Invokable<?, ?> method = method(CloudApi.class, "replaceHardDisks", VirtualMachineDto.class,
            DiskManagementDto[].class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualMachinePut(), new DiskManagementDto[] { first, second })));

      String editLink = CloudResources.hardDiskPut().getEditLink().getHref();
      assertRequestLineEquals(request,
            "PUT http://localhost/api/cloud/virtualdatacenters/1/virtualappliances/1/virtualmachines/1/storage/disks HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + AcceptedRequestDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader("<links><link href=\"" + editLink + "\" rel=\"disk\"/><link href=\""
            + editLink + "second\" rel=\"disk\"/></links>"), LinksDto.class, LinksDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ReturnTaskReferenceOrNull.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   /*********************** Hard disks ***********************/

   public void testListHardDisks() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listHardDisks", VirtualDatacenterDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut())));

      assertRequestLineEquals(request, "GET http://localhost/api/cloud/virtualdatacenters/1/disks HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + DisksManagementDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testGetHardDisk() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "getHardDisk", VirtualDatacenterDto.class, Integer.class);
      ;
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut(), 1)));

      assertRequestLineEquals(request, "GET http://localhost/api/cloud/virtualdatacenters/1/disks/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + DiskManagementDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, NullOnNotFoundOr404.class);

      checkFilters(request);
   }

   public void testCreateHardDisk() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "createHardDisk", VirtualDatacenterDto.class,
            DiskManagementDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut(), CloudResources.hardDiskPost())));

      assertRequestLineEquals(request, "POST http://localhost/api/cloud/virtualdatacenters/1/disks HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + DiskManagementDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(CloudResources.hardDiskPostPayload()), DiskManagementDto.class,
            DiskManagementDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testDeleteHardDisk() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "deleteHardDisk", DiskManagementDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.hardDiskPut())));

      assertRequestLineEquals(request, "DELETE http://localhost/api/cloud/virtualdatacenters/1/disks/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   /*********************** Volumes ***********************/

   public void testListVolumes() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listVolumes", VirtualDatacenterDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut())));

      assertRequestLineEquals(request, "GET http://localhost/api/cloud/virtualdatacenters/1/volumes HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VolumesManagementDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testListVolumesWithOptions() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listVolumes", VirtualDatacenterDto.class, VolumeOptions.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(
            method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut(), VolumeOptions.builder()
                  .onlyAvailable(true).build())));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/volumes?available=true HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VolumesManagementDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testListVolumesWithFilterOptions() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "listVolumes", VirtualDatacenterDto.class, VolumeOptions.class);

      GeneratedHttpRequest request = processor.apply(Invocation.create(
            method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut(), VolumeOptions.builder().has("vol")
                  .orderBy(OrderBy.NAME).ascendant(true).build())));

      assertRequestLineEquals(request,
            "GET http://localhost/api/cloud/virtualdatacenters/1/volumes?has=vol&by=name&asc=true HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VolumesManagementDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testGetVolume() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "getVolume", VirtualDatacenterDto.class, Integer.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut(), 1)));

      assertRequestLineEquals(request, "GET http://localhost/api/cloud/virtualdatacenters/1/volumes/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VolumeManagementDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, NullOnNotFoundOr404.class);

      checkFilters(request);
   }

   public void testCreateVolume() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "createVolume", VirtualDatacenterDto.class,
            VolumeManagementDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.virtualDatacenterPut(), CloudResources.volumePost())));

      assertRequestLineEquals(request, "POST http://localhost/api/cloud/virtualdatacenters/1/volumes HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + VolumeManagementDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(CloudResources.volumePostPayload()), VolumeManagementDto.class,
            VolumeManagementDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testUpdateVolume() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "updateVolume", VolumeManagementDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.volumePut())));

      assertRequestLineEquals(request, "PUT http://localhost/api/cloud/virtualdatacenters/1/volumes/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + AcceptedRequestDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(CloudResources.volumePutPayload()), VolumeManagementDto.class,
            VolumeManagementDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ReturnTaskReferenceOrNull.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testDeleteVolume() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "deleteVolume", VolumeManagementDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.volumePut())));

      assertRequestLineEquals(request, "DELETE http://localhost/api/cloud/virtualdatacenters/1/volumes/1 HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "");
      assertPayloadEquals(request, null, null, false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, null);

      checkFilters(request);
   }

   public void testMoveVolume() throws SecurityException, NoSuchMethodException, IOException {
      Invokable<?, ?> method = method(CloudApi.class, "moveVolume", VolumeManagementDto.class,
            VirtualDatacenterDto.class);
      GeneratedHttpRequest request = processor.apply(Invocation.create(method,
            ImmutableList.<Object> of(CloudResources.volumePut(), CloudResources.virtualDatacenterPut())));

      assertRequestLineEquals(request,
            "POST http://localhost/api/cloud/virtualdatacenters/1/volumes/1/action/move HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Accept: " + MovedVolumeDto.BASE_MEDIA_TYPE + "\n");
      assertPayloadEquals(request, withHeader(CloudResources.virtualDatacenterRefPayload()), LinksDto.class,
            LinksDto.BASE_MEDIA_TYPE, false);

      assertResponseParserClassEquals(method, request, ParseXMLWithJAXB.class);
      assertSaxResponseParserClassEquals(method, null);
      assertFallbackClassEquals(method, MovedVolume.class);

      checkFilters(request);
   }
}
