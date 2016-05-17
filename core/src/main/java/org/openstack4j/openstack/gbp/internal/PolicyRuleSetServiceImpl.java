package org.openstack4j.openstack.gbp.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import org.openstack4j.api.gbp.PolicyRuleSetService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.gbp.PolicyRuleSet;
import org.openstack4j.openstack.gbp.domain.GbpPolicyRuleSet;
import org.openstack4j.openstack.gbp.domain.GbpPolicyRuleSet.PolicyRuleSets;
import org.openstack4j.openstack.networking.internal.BaseNetworkingServices;

public class PolicyRuleSetServiceImpl extends BaseNetworkingServices implements PolicyRuleSetService {

    @Override
    public List<? extends PolicyRuleSet> list() {
        return get(PolicyRuleSets.class, uri("/grouppolicy/policy_rule_sets")).execute().getList();
    }

    @Override
    public List<? extends PolicyRuleSet> list(Map<String, String> filteringParams) {
        Invocation<PolicyRuleSets> policyrulesetsInvocation = buildInvocation(filteringParams);
        return policyrulesetsInvocation.execute().getList();
    }
    
    private Invocation<PolicyRuleSets> buildInvocation(Map<String, String> filteringParams) {
        Invocation<PolicyRuleSets> policyrulesetsInvocation = get(PolicyRuleSets.class, "/grouppolicy/policy_rule_sets");
        if (filteringParams == null) { 
            return policyrulesetsInvocation;
        } 
        if (filteringParams != null) {
            for (Map.Entry<String, String> entry : filteringParams.entrySet()) {
                policyrulesetsInvocation = policyrulesetsInvocation.param(entry.getKey(), entry.getValue());
            }
        }
        return policyrulesetsInvocation;
    }

    @Override
    public PolicyRuleSet get(String id) {
        checkNotNull(id);
        return get(GbpPolicyRuleSet.class, uri("/grouppolicy/policy_rule_sets/%s", id)).execute();
    }

    @Override
    public ActionResponse delete(String id) {
        checkNotNull(id);
        return deleteWithResponse(uri("/grouppolicy/policy_rule_sets/%s", id)).execute();
    }

    @Override
    public PolicyRuleSet create(PolicyRuleSet policyRuleSet) {
        return post(GbpPolicyRuleSet.class, uri("/grouppolicy/policy_rule_sets")).entity(policyRuleSet).execute();
    }

    @Override
    public PolicyRuleSet update(String policyRuleSetId, PolicyRuleSet policyRuleSet) {
        checkNotNull(policyRuleSetId);
        checkNotNull(policyRuleSet);
        return put(GbpPolicyRuleSet.class, uri("/grouppolicy/policy_rule_sets/%s", policyRuleSetId)).entity(policyRuleSet).execute();

    }

}
