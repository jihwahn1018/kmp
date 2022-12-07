package com.tmax.kmp.constants;

import java.util.HashMap;
import java.util.Map;

public class ApiListConstants {
    public static final HashMap<String, String> CORE_API_LIST = new HashMap<String, String>() {
        {
            put("connectDeleteNamespacedPodProxy", "DELETE /api/v1/namespaces/{namespace}/pods/{name}/proxy");
            put("connectDeleteNamespacedPodProxyWithPath", "DELETE /api/v1/namespaces/{namespace}/pods/{name}/proxy/{path}");
            put("connectDeleteNamespacedServiceProxy", "DELETE /api/v1/namespaces/{namespace}/services/{name}/proxy");
            put("connectDeleteNamespacedServiceProxyWithPath","DELETE /api/v1/namespaces/{namespace}/services/{name}/proxy/{path}");
            put("connectDeleteNodeProxy","DELETE /api/v1/nodes/{name}/proxy");
            put("connectDeleteNodeProxyWithPath","DELETE /api/v1/nodes/{name}/proxy/{path}");
            put("connectGetNamespacedPodAttach","GET /api/v1/namespaces/{namespace}/pods/{name}/attach");
            put("connectGetNamespacedPodExec","GET /api/v1/namespaces/{namespace}/pods/{name}/exec");
            put("connectGetNamespacedPodPortforward","GET /api/v1/namespaces/{namespace}/pods/{name}/portforward");
            put("connectGetNamespacedPodProxy","GET /api/v1/namespaces/{namespace}/pods/{name}/proxy");
            put("connectGetNamespacedPodProxyWithPath","GET /api/v1/namespaces/{namespace}/pods/{name}/proxy/{path}");
            put("connectGetNamespacedServiceProxy","GET /api/v1/namespaces/{namespace}/services/{name}/proxy");
            put("connectGetNamespacedServiceProxyWithPath","GET /api/v1/namespaces/{namespace}/services/{name}/proxy/{path}");
            put("connectGetNodeProxy","GET /api/v1/nodes/{name}/proxy");
            put("connectGetNodeProxyWithPath","GET /api/v1/nodes/{name}/proxy/{path}");
            put("connectHeadNamespacedPodProxy","HEAD /api/v1/namespaces/{namespace}/pods/{name}/proxy");
            put("connectHeadNamespacedPodProxyWithPath","HEAD /api/v1/namespaces/{namespace}/pods/{name}/proxy/{path}");
            put("connectHeadNamespacedServiceProxy","HEAD /api/v1/namespaces/{namespace}/services/{name}/proxy");
            put("connectHeadNamespacedServiceProxyWithPath","HEAD /api/v1/namespaces/{namespace}/services/{name}/proxy/{path}");
            put("connectHeadNodeProxy","HEAD /api/v1/nodes/{name}/proxy");
            put("connectHeadNodeProxyWithPath","HEAD /api/v1/nodes/{name}/proxy/{path}");
            put("connectOptionsNamespacedPodProxy","OPTIONS /api/v1/namespaces/{namespace}/pods/{name}/proxy");
            put("connectOptionsNamespacedPodProxyWithPath","OPTIONS /api/v1/namespaces/{namespace}/pods/{name}/proxy/{path}");
            put("connectOptionsNamespacedServiceProxy","OPTIONS /api/v1/namespaces/{namespace}/services/{name}/proxy");
            put("connectOptionsNamespacedServiceProxyWithPath","OPTIONS /api/v1/namespaces/{namespace}/services/{name}/proxy/{path}");
            put("connectOptionsNodeProxy","OPTIONS /api/v1/nodes/{name}/proxy");
            put("connectOptionsNodeProxyWithPath","OPTIONS /api/v1/nodes/{name}/proxy/{path}");
            put("connectPatchNamespacedPodProxy","PATCH /api/v1/namespaces/{namespace}/pods/{name}/proxy");
            put("connectPatchNamespacedPodProxyWithPath","PATCH /api/v1/namespaces/{namespace}/pods/{name}/proxy/{path}");
            put("connectPatchNamespacedServiceProxy","PATCH /api/v1/namespaces/{namespace}/services/{name}/proxy");
            put("connectPatchNamespacedServiceProxyWithPath","PATCH /api/v1/namespaces/{namespace}/services/{name}/proxy/{path}");
            put("connectPatchNodeProxy","PATCH /api/v1/nodes/{name}/proxy");
            put("connectPatchNodeProxyWithPath","PATCH /api/v1/nodes/{name}/proxy/{path}");
            put("connectPostNamespacedPodAttach","POST /api/v1/namespaces/{namespace}/pods/{name}/attach");
            put("connectPostNamespacedPodExec","POST /api/v1/namespaces/{namespace}/pods/{name}/exec");
            put("connectPostNamespacedPodPortforward","POST /api/v1/namespaces/{namespace}/pods/{name}/portforward");
            put("connectPostNamespacedPodProxy","POST /api/v1/namespaces/{namespace}/pods/{name}/proxy");
            put("connectPostNamespacedPodProxyWithPath","POST /api/v1/namespaces/{namespace}/pods/{name}/proxy/{path}");
            put("connectPostNamespacedServiceProxy","POST /api/v1/namespaces/{namespace}/services/{name}/proxy");
            put("connectPostNamespacedServiceProxyWithPath","POST /api/v1/namespaces/{namespace}/services/{name}/proxy/{path}");
            put("connectPostNodeProxy", "POST /api/v1/nodes/{name}/proxy");
            put("connectPostNodeProxyWithPath", "POST /api/v1/nodes/{name}/proxy/{path}");
            put("connectPutNamespacedPodProxy", "PUT /api/v1/namespaces/{namespace}/pods/{name}/proxy");
            put("connectPutNamespacedPodProxyWithPath", "PUT /api/v1/namespaces/{namespace}/pods/{name}/proxy/{path}");
            put("connectPutNamespacedServiceProxy", "PUT /api/v1/namespaces/{namespace}/services/{name}/proxy");
            put("connectPutNamespacedServiceProxyWithPath", "PUT /api/v1/namespaces/{namespace}/services/{name}/proxy/{path}");
            put("connectPutNodeProxy", "PUT /api/v1/nodes/{name}/proxy");
            put("connectPutNodeProxyWithPath", "PUT /api/v1/nodes/{name}/proxy/{path}");
            put("createNamespace", "POST /api/v1/namespaces");
            put("createNamespacedBinding", "POST /api/v1/namespaces/{namespace}/bindings");
            put("createNamespacedConfigMap", "POST /api/v1/namespaces/{namespace}/configmaps");
            put("createNamespacedEndpoints", "POST /api/v1/namespaces/{namespace}/endpoints");
            put("createNamespacedEvent", "POST /api/v1/namespaces/{namespace}/events");
            put("createNamespacedLimitRange", "POST /api/v1/namespaces/{namespace}/limitranges");
            put("createNamespacedPersistentVolumeClaim", "POST /api/v1/namespaces/{namespace}/persistentvolumeclaims");
            put("createNamespacedPod", "POST /api/v1/namespaces/{namespace}/pods");
            put("createNamespacedPodBinding", "POST /api/v1/namespaces/{namespace}/pods/{name}/binding");
            put("createNamespacedPodEviction", "POST /api/v1/namespaces/{namespace}/pods/{name}/eviction");
            put("createNamespacedPodTemplate", "POST /api/v1/namespaces/{namespace}/podtemplates");
            put("createNamespacedReplicationController", "POST /api/v1/namespaces/{namespace}/replicationcontrollers");
            put("createNamespacedResourceQuota", "POST /api/v1/namespaces/{namespace}/resourcequotas");
            put("createNamespacedSecret", "POST /api/v1/namespaces/{namespace}/secrets");
            put("createNamespacedService", "POST /api/v1/namespaces/{namespace}/services");
            put("createNamespacedServiceAccount", "POST /api/v1/namespaces/{namespace}/serviceaccounts");
            put("createNamespacedServiceAccountToken", "POST /api/v1/namespaces/{namespace}/serviceaccounts/{name}/token");
            put("createNode", "POST /api/v1/nodes");
            put("createPersistentVolume", "POST /api/v1/persistentvolumes");
            put("deleteCollectionNamespacedConfigMap", "DELETE /api/v1/namespaces/{namespace}/configmaps");
            put("deleteCollectionNamespacedEndpoints", "DELETE /api/v1/namespaces/{namespace}/endpoints");
            put("deleteCollectionNamespacedEvent", "DELETE /api/v1/namespaces/{namespace}/events");
            put("deleteCollectionNamespacedLimitRange", "DELETE /api/v1/namespaces/{namespace}/limitranges");
            put("deleteCollectionNamespacedPersistentVolumeClaim", "DELETE /api/v1/namespaces/{namespace}/persistentvolumeclaims");
            put("deleteCollectionNamespacedPod", "DELETE /api/v1/namespaces/{namespace}/pods");
            put("deleteCollectionNamespacedPodTemplate", "DELETE /api/v1/namespaces/{namespace}/podtemplates");
            put("deleteCollectionNamespacedReplicationController", "DELETE /api/v1/namespaces/{namespace}/replicationcontrollers");
            put("deleteCollectionNamespacedResourceQuota", "DELETE /api/v1/namespaces/{namespace}/resourcequotas");
            put("deleteCollectionNamespacedSecret", "DELETE /api/v1/namespaces/{namespace}/secrets");
            put("deleteCollectionNamespacedService", "DELETE /api/v1/namespaces/{namespace}/services");
            put("deleteCollectionNamespacedServiceAccount", "DELETE /api/v1/namespaces/{namespace}/serviceaccounts");
            put("deleteCollectionNode", "DELETE /api/v1/nodes");
            put("deleteCollectionPersistentVolume", "DELETE /api/v1/persistentvolumes");
            put("deleteNamespace", "DELETE /api/v1/namespaces/{name}");
            put("deleteNamespacedConfigMap", "DELETE /api/v1/namespaces/{namespace}/configmaps/{name}");
            put("deleteNamespacedEndpoints", "DELETE /api/v1/namespaces/{namespace}/endpoints/{name}");
            put("deleteNamespacedEvent", "DELETE /api/v1/namespaces/{namespace}/events/{name}");
            put("deleteNamespacedLimitRange", "DELETE /api/v1/namespaces/{namespace}/limitranges/{name}");
            put("deleteNamespacedPersistentVolumeClaim", "DELETE /api/v1/namespaces/{namespace}/persistentvolumeclaims/{name}");
            put("deleteNamespacedPod", "DELETE /api/v1/namespaces/{namespace}/pods/{name}");
            put("deleteNamespacedPodTemplate", "DELETE /api/v1/namespaces/{namespace}/podtemplates/{name}");
            put("deleteNamespacedReplicationController", "DELETE /api/v1/namespaces/{namespace}/replicationcontrollers/{name}");
            put("deleteNamespacedResourceQuota", "DELETE /api/v1/namespaces/{namespace}/resourcequotas/{name}");
            put("deleteNamespacedSecret", "DELETE /api/v1/namespaces/{namespace}/secrets/{name}");
            put("deleteNamespacedService", "DELETE /api/v1/namespaces/{namespace}/services/{name}");
            put("deleteNamespacedServiceAccount", "DELETE /api/v1/namespaces/{namespace}/serviceaccounts/{name}");
            put("deleteNode", "DELETE /api/v1/nodes/{name}");
            put("deletePersistentVolume", "DELETE /api/v1/persistentvolumes/{name}");
            put("getAPIResources", "GET /api/v1/");
            put("listComponentStatus", "GET /api/v1/componentstatuses");
            put("listConfigMapForAllNamespaces", "GET /api/v1/configmaps");
            put("listEndpointsForAllNamespaces", "GET /api/v1/endpoints");
            put("listEventForAllNamespaces", "GET /api/v1/events");
            put("listLimitRangeForAllNamespaces", "GET /api/v1/limitranges");
            put("listNamespace", "GET /api/v1/namespaces");
            put("listNamespacedConfigMap", "GET /api/v1/namespaces/{namespace}/configmaps");
            put("listNamespacedEndpoints", "GET /api/v1/namespaces/{namespace}/endpoints");
            put("listNamespacedEvent", "GET /api/v1/namespaces/{namespace}/events");
            put("listNamespacedLimitRange", "GET /api/v1/namespaces/{namespace}/limitranges");
            put("listNamespacedPersistentVolumeClaim", "GET /api/v1/namespaces/{namespace}/persistentvolumeclaims");
            put("listNamespacedPod", "GET /api/v1/namespaces/{namespace}/pods");
            put("listNamespacedPodTemplate", "GET /api/v1/namespaces/{namespace}/podtemplates");
            put("listNamespacedReplicationController", "GET /api/v1/namespaces/{namespace}/replicationcontrollers");
            put("listNamespacedResourceQuota", "GET /api/v1/namespaces/{namespace}/resourcequotas");
            put("listNamespacedSecret", "GET /api/v1/namespaces/{namespace}/secrets");
            put("listNamespacedService", "GET /api/v1/namespaces/{namespace}/services");
            put("listNamespacedServiceAccount", "GET /api/v1/namespaces/{namespace}/serviceaccounts");
            put("listNode", "GET /api/v1/nodes");
            put("listPersistentVolume", "GET /api/v1/persistentvolumes");
            put("listPersistentVolumeClaimForAllNamespaces", "GET /api/v1/persistentvolumeclaims");
            put("listPodForAllNamespaces", "GET /api/v1/pods");
            put("listPodTemplateForAllNamespaces", "GET /api/v1/podtemplates");
            put("listReplicationControllerForAllNamespaces", "GET /api/v1/replicationcontrollers");
            put("listResourceQuotaForAllNamespaces", "GET /api/v1/resourcequotas");
            put("listSecretForAllNamespaces", "GET /api/v1/secrets");
            put("listServiceAccountForAllNamespaces", "GET /api/v1/serviceaccounts");
            put("listServiceForAllNamespaces", "GET /api/v1/services");
            put("patchNamespace", "PATCH /api/v1/namespaces/{name}");
            put("patchNamespaceStatus", "PATCH /api/v1/namespaces/{name}/status");
            put("patchNamespacedConfigMap", "PATCH /api/v1/namespaces/{namespace}/configmaps/{name}");
            put("patchNamespacedEndpoints", "PATCH /api/v1/namespaces/{namespace}/endpoints/{name}");
            put("patchNamespacedEvent", "PATCH /api/v1/namespaces/{namespace}/events/{name}");
            put("patchNamespacedLimitRange", "PATCH /api/v1/namespaces/{namespace}/limitranges/{name}");
            put("patchNamespacedPersistentVolumeClaim", "PATCH /api/v1/namespaces/{namespace}/persistentvolumeclaims/{name}");
            put("patchNamespacedPersistentVolumeClaimStatus", "PATCH /api/v1/namespaces/{namespace}/persistentvolumeclaims/{name}/status");
            put("patchNamespacedPod", "PATCH /api/v1/namespaces/{namespace}/pods/{name}");
            put("patchNamespacedPodEphemeralcontainers", "PATCH /api/v1/namespaces/{namespace}/pods/{name}/ephemeralcontainers");
            put("patchNamespacedPodStatus", "PATCH /api/v1/namespaces/{namespace}/pods/{name}/status");
            put("patchNamespacedPodTemplate", "PATCH /api/v1/namespaces/{namespace}/podtemplates/{name}");
            put("patchNamespacedReplicationController", "PATCH /api/v1/namespaces/{namespace}/replicationcontrollers/{name}");
            put("patchNamespacedReplicationControllerScale", "PATCH /api/v1/namespaces/{namespace}/replicationcontrollers/{name}/scale");
            put("patchNamespacedReplicationControllerStatus", "PATCH /api/v1/namespaces/{namespace}/replicationcontrollers/{name}/status");
            put("patchNamespacedResourceQuota", "PATCH /api/v1/namespaces/{namespace}/resourcequotas/{name}");
            put("patchNamespacedResourceQuotaStatus", "PATCH /api/v1/namespaces/{namespace}/resourcequotas/{name}/status");
            put("patchNamespacedSecret", "PATCH /api/v1/namespaces/{namespace}/secrets/{name}");
            put("patchNamespacedService", "PATCH /api/v1/namespaces/{namespace}/services/{name}");
            put("patchNamespacedServiceAccount", "PATCH /api/v1/namespaces/{namespace}/serviceaccounts/{name}");
            put("patchNamespacedServiceStatus", "PATCH /api/v1/namespaces/{namespace}/services/{name}/status");
            put("patchNode", "PATCH /api/v1/nodes/{name}");
            put("patchNodeStatus", "PATCH /api/v1/nodes/{name}/status");
            put("patchPersistentVolume", "PATCH /api/v1/persistentvolumes/{name}");
            put("patchPersistentVolumeStatus", "PATCH /api/v1/persistentvolumes/{name}/status");
            put("readComponentStatus", "GET /api/v1/componentstatuses/{name}");
            put("readNamespace", "GET /api/v1/namespaces/{name}");
            put("readNamespaceStatus", "GET /api/v1/namespaces/{name}/status");
            put("readNamespacedConfigMap", "GET /api/v1/namespaces/{namespace}/configmaps/{name}");
            put("readNamespacedEndpoints", "GET /api/v1/namespaces/{namespace}/endpoints/{name}");
            put("readNamespacedEvent", "GET /api/v1/namespaces/{namespace}/events/{name}");
            put("readNamespacedLimitRange", "GET /api/v1/namespaces/{namespace}/limitranges/{name}");
            put("readNamespacedPersistentVolumeClaim", "GET /api/v1/namespaces/{namespace}/persistentvolumeclaims/{name}");
            put("readNamespacedPersistentVolumeClaimStatus", "GET /api/v1/namespaces/{namespace}/persistentvolumeclaims/{name}/status");
            put("readNamespacedPod", "GET /api/v1/namespaces/{namespace}/pods/{name}");
            put("readNamespacedPodEphemeralcontainers", "GET /api/v1/namespaces/{namespace}/pods/{name}/ephemeralcontainers");
            put("readNamespacedPodLog", "GET /api/v1/namespaces/{namespace}/pods/{name}/log");
            put("readNamespacedPodStatus", "GET /api/v1/namespaces/{namespace}/pods/{name}/status");
            put("readNamespacedPodTemplate", "GET /api/v1/namespaces/{namespace}/podtemplates/{name}");
            put("readNamespacedReplicationController", "GET /api/v1/namespaces/{namespace}/replicationcontrollers/{name}");
            put("readNamespacedReplicationControllerScale", "GET /api/v1/namespaces/{namespace}/replicationcontrollers/{name}/scale");
            put("readNamespacedReplicationControllerStatus", "GET /api/v1/namespaces/{namespace}/replicationcontrollers/{name}/status");
            put("readNamespacedResourceQuota", "GET /api/v1/namespaces/{namespace}/resourcequotas/{name}");
            put("readNamespacedResourceQuotaStatus", "GET /api/v1/namespaces/{namespace}/resourcequotas/{name}/status");
            put("readNamespacedSecret", "GET /api/v1/namespaces/{namespace}/secrets/{name}");
            put("readNamespacedService", "GET /api/v1/namespaces/{namespace}/services/{name}");
            put("readNamespacedServiceAccount", "GET /api/v1/namespaces/{namespace}/serviceaccounts/{name}");
            put("readNamespacedServiceStatus", "GET /api/v1/namespaces/{namespace}/services/{name}/status");
            put("readNode", "GET /api/v1/nodes/{name}");
            put("readNodeStatus", "GET /api/v1/nodes/{name}/status");
            put("readPersistentVolume", "GET /api/v1/persistentvolumes/{name}");
            put("readPersistentVolumeStatus", "GET /api/v1/persistentvolumes/{name}/status");
            put("replaceNamespace", "PUT /api/v1/namespaces/{name}");
            put("replaceNamespaceFinalize", "PUT /api/v1/namespaces/{name}/finalize");
            put("replaceNamespaceStatus", "PUT /api/v1/namespaces/{name}/status");
            put("replaceNamespacedConfigMap", "PUT /api/v1/namespaces/{namespace}/configmaps/{name}");
            put("replaceNamespacedEndpoints", "PUT /api/v1/namespaces/{namespace}/endpoints/{name}");
            put("replaceNamespacedEvent", "PUT /api/v1/namespaces/{namespace}/events/{name}");
            put("replaceNamespacedLimitRange", "PUT /api/v1/namespaces/{namespace}/limitranges/{name}");
            put("replaceNamespacedPersistentVolumeClaim", "PUT /api/v1/namespaces/{namespace}/persistentvolumeclaims/{name}");
            put("replaceNamespacedPersistentVolumeClaimStatus", "PUT /api/v1/namespaces/{namespace}/persistentvolumeclaims/{name}/status");
            put("replaceNamespacedPod", "PUT /api/v1/namespaces/{namespace}/pods/{name}");
            put("replaceNamespacedPodEphemeralcontainers", "PUT /api/v1/namespaces/{namespace}/pods/{name}/ephemeralcontainers");
            put("replaceNamespacedPodStatus", "PUT /api/v1/namespaces/{namespace}/pods/{name}/status");
            put("replaceNamespacedPodTemplate", "PUT /api/v1/namespaces/{namespace}/podtemplates/{name}");
            put("replaceNamespacedReplicationController", "PUT /api/v1/namespaces/{namespace}/replicationcontrollers/{name}");
            put("replaceNamespacedReplicationControllerScale", "PUT /api/v1/namespaces/{namespace}/replicationcontrollers/{name}/scale");
            put("replaceNamespacedReplicationControllerStatus", "PUT /api/v1/namespaces/{namespace}/replicationcontrollers/{name}/status");
            put("replaceNamespacedResourceQuota", "PUT /api/v1/namespaces/{namespace}/resourcequotas/{name}");
            put("replaceNamespacedResourceQuotaStatus", "PUT /api/v1/namespaces/{namespace}/resourcequotas/{name}/status");
            put("replaceNamespacedSecret", "PUT /api/v1/namespaces/{namespace}/secrets/{name}");
            put("replaceNamespacedService", "PUT /api/v1/namespaces/{namespace}/services/{name}");
            put("replaceNamespacedServiceAccount", "PUT /api/v1/namespaces/{namespace}/serviceaccounts/{name}");
            put("replaceNamespacedServiceStatus", "PUT /api/v1/namespaces/{namespace}/services/{name}/status");
            put("replaceNode", "PUT /api/v1/nodes/{name}");
            put("replaceNodeStatus", "PUT /api/v1/nodes/{name}/status");
            put("replacePersistentVolume", "PUT /api/v1/persistentvolumes/{name}");
            put("replacePersistentVolumeStatus", "PUT /api/v1/persistentvolumes/{name}/status");
        }
    };
    
}
